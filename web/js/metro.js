// SmartCity360 - Urban Operations Platform (Hashen Liyanaarachchi)
// Client Script coordinating AJAX REST endpoints with Java Controller

document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
    initPartA();
    initPartB();
    initPartC();
    initPartD();
    initPartE();
});

// Navigation Tabs
function initNavigation() {
    const tabs = document.querySelectorAll('.nav-tab-btn');
    const contents = document.querySelectorAll('.tab-content');

    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const targetId = tab.getAttribute('data-target');
            
            tabs.forEach(t => t.classList.remove('active'));
            contents.forEach(c => c.classList.remove('active'));

            tab.classList.add('active');
            const targetContent = document.getElementById(targetId);
            if (targetContent) {
                targetContent.classList.add('active');
            }
        });
    });
}

// ==========================================
// PART A: DECORATOR PATTERN (STREAM SECURITY)
// ==========================================
function initPartA() {
    const btnTransmit = document.getElementById('btnTransmitStream');
    if (!btnTransmit) return;

    btnTransmit.addEventListener('click', async () => {
        const payload = document.getElementById('streamPayload').value;
        const enableAuth = document.getElementById('chkAuth').checked;
        const token = document.getElementById('txtToken').value;
        const enableEncrypt = document.getElementById('chkEncrypt').checked;
        const enableCompress = document.getElementById('chkCompress').checked;

        btnTransmit.disabled = true;
        btnTransmit.innerHTML = `<svg class="tab-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 6v6l4 2"/></svg> Transmitting Telemetry Stream...`;

        try {
            const res = await fetch('/api/stream/security', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ payload, enableAuth, token, enableEncrypt, enableCompress })
            });

            const data = await res.json();
            renderStreamResults(data);
        } catch (err) {
            console.error('Part A Error:', err);
            alert('Failed to transmit security stream: ' + err.message);
        } finally {
            btnTransmit.disabled = false;
            btnTransmit.innerHTML = `<svg class="tab-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/><path d="m9 12 2 2 4-4"/></svg> Execute Secured Data Transmission`;
        }
    });
}

function renderStreamResults(data) {
    document.getElementById('streamRawSize').innerText = data.rawSize + ' B';
    document.getElementById('streamWireSize').innerText = data.wireSize + ' B';
    document.getElementById('wireOutputBox').innerText = data.transmittedWirePayload;
    document.getElementById('decipheredOutputBox').innerText = data.decipheredPayload;

    const stagesContainer = document.getElementById('pipelineStagesList');
    stagesContainer.innerHTML = '';
    if (data.pipelineStages && data.pipelineStages.length > 0) {
        data.pipelineStages.forEach((stage, idx) => {
            const div = document.createElement('div');
            div.className = 'pipeline-step';
            div.innerHTML = `<span class="step-num">Layer 0${idx + 1}:</span> <span>${escapeHtml(stage)}</span>`;
            stagesContainer.appendChild(div);
        });
    }
}

// ==========================================
// PART B: COMPOSITE PATTERN (POWER GRID)
// ==========================================
async function initPartB() {
    await refreshGridStatus();
    const btnRefresh = document.getElementById('btnRefreshGrid');
    if (btnRefresh) {
        btnRefresh.addEventListener('click', refreshGridStatus);
    }
}

async function refreshGridStatus() {
    try {
        const res = await fetch('/api/grid/status');
        const data = await res.json();
        
        document.getElementById('gridTotalDemand').innerText = data.totalGridDemandKw.toFixed(1) + ' kW';
        document.getElementById('gridStatusBadge').innerHTML = data.isGridEnergized
            ? '<span class="badge-energized">ALL FEEDERS ACTIVE</span>'
            : '<span class="badge-cut">PARTIALLY / FULLY DE-ENERGIZED</span>';

        const treeContainer = document.getElementById('gridTreeContainer');
        treeContainer.innerHTML = '';
        renderGridNode(data.gridHierarchy, treeContainer);
    } catch (err) {
        console.error('Part B Grid error:', err);
    }
}

function renderGridNode(node, container) {
    const nodeDiv = document.createElement('div');
    nodeDiv.className = 'tree-node-item';

    const infoDiv = document.createElement('div');
    infoDiv.className = 'node-info';
    infoDiv.innerHTML = `
        <span class="node-title">${escapeHtml(node.title)}</span>
        <span class="node-category">${node.category}</span>
        <span class="node-demand">${node.demandKw.toFixed(1)} kW</span>
        ${node.energized ? '<span class="badge-energized">Energized</span>' : '<span class="badge-cut">Power Cut</span>'}
    `;

    const actionBtn = document.createElement('button');
    actionBtn.className = 'btn-secondary';
    actionBtn.style.padding = '0.3rem 0.65rem';
    actionBtn.style.fontSize = '0.78rem';
    actionBtn.innerText = node.energized ? 'Cut Power' : 'Energize';
    actionBtn.addEventListener('click', async () => {
        await toggleNodePower(node.title, !node.energized);
    });

    nodeDiv.appendChild(infoDiv);
    nodeDiv.appendChild(actionBtn);
    container.appendChild(nodeDiv);

    if (node.isComposite && node.children && node.children.length > 0) {
        const childContainer = document.createElement('div');
        childContainer.className = 'tree-node';
        node.children.forEach(child => renderGridNode(child, childContainer));
        container.appendChild(childContainer);
    }
}

async function toggleNodePower(targetNode, energize) {
    try {
        const res = await fetch('/api/grid/power-toggle', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ targetNode, energize })
        });
        const data = await res.json();
        await refreshGridStatus();
    } catch (err) {
        alert('Failed to toggle node power: ' + err.message);
    }
}

// ==========================================
// PART C: CHAIN OF RESPONSIBILITY (INCIDENTS)
// ==========================================
async function initPartC() {
    await refreshTicketHistory();

    const form = document.getElementById('incidentForm');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const citizenName = document.getElementById('txtCitizenName').value;
        const incidentDetail = document.getElementById('txtIncidentDetail').value;
        const severity = document.getElementById('selSeverity').value;

        try {
            const res = await fetch('/api/incident/report', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ citizenName, incidentDetail, severity })
            });
            const resolution = await res.json();
            displayTicketResolution(resolution);
            await refreshTicketHistory();
            form.reset();
        } catch (err) {
            alert('Failed to submit incident: ' + err.message);
        }
    });
}

function displayTicketResolution(res) {
    const card = document.getElementById('latestResolutionCard');
    const placeholder = document.getElementById('resolutionPlaceholder');
    if (placeholder) placeholder.style.display = 'none';
    if (card) card.style.display = 'block';

    const ticketCode = res.ticketCode || (res.ticket && res.ticket.ticketCode) || 'TCK-000';
    const citizen = res.citizenName || (res.ticket && res.ticket.citizenName) || 'Citizen';
    const severity = res.severity || (res.ticket && res.ticket.severity) || 'MINOR';
    const handler = res.resolvedByOffice || res.handlerDesignation || 'Municipal Authority';
    const summary = res.problemSummary || (res.ticket && res.ticket.problemSummary) || (res.ticket && res.ticket.issueDescription) || '';
    const audit = res.auditLog || res.auditTrail || [];

    document.getElementById('resTicketCode').innerText = ticketCode;
    document.getElementById('resCitizen').innerText = citizen;
    document.getElementById('resSeverity').innerText = severity;
    document.getElementById('resHandler').innerText = handler;
    document.getElementById('resDept').innerText = handler + ' (Civic Operations Division)';
    document.getElementById('resVerdict').innerText = res.resolved ? ('ACTION EXECUTED: Dispatched unit for ' + summary) : 'PENDING REVIEW';

    const trailDiv = document.getElementById('resAuditTrail');
    trailDiv.innerHTML = '';
    if (audit && audit.length > 0) {
        audit.forEach(step => {
            const p = document.createElement('div');
            p.className = 'pipeline-step';
            p.style.padding = '0.4rem 0.75rem';
            p.style.fontSize = '0.8rem';
            p.innerText = step;
            trailDiv.appendChild(p);
        });
    }
}

async function refreshTicketHistory() {
    try {
        const res = await fetch('/api/incident/report');
        const data = await res.json();
        const tbody = document.getElementById('ticketHistoryBody');
        if (!tbody) return;
        tbody.innerHTML = '';

        const historyList = data.history || [];
        if (historyList.length > 0) {
            historyList.forEach(item => {
                const tr = document.createElement('tr');
                const ticketCode = item.ticketCode || (item.ticket && item.ticket.ticketCode) || '--';
                const citizen = item.citizenName || (item.ticket && item.ticket.citizenName) || 'Citizen';
                const description = item.problemSummary || (item.ticket && item.ticket.problemSummary) || (item.ticket && item.ticket.issueDescription) || '--';
                const severity = item.severity || (item.ticket && item.ticket.severity) || 'MINOR';
                const handler = item.resolvedByOffice || item.handlerDesignation || 'Assigned Officer';

                const sevClass = severity === 'MINOR' ? 'sev-minor' :
                                 severity === 'URGENT' ? 'sev-urgent' : 'sev-critical';

                tr.innerHTML = `
                    <td><strong>${escapeHtml(ticketCode)}</strong></td>
                    <td>${escapeHtml(citizen)}</td>
                    <td>${escapeHtml(description)}</td>
                    <td><span class="${sevClass}">${escapeHtml(severity)}</span></td>
                    <td><strong>${escapeHtml(handler)}</strong></td>
                    <td><span class="badge-energized">RESOLVED</span></td>
                `;
                tbody.appendChild(tr);
            });
        } else {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align:center; color:var(--text-muted); padding:1.5rem;">No incident reports logged yet.</td></tr>`;
        }
    } catch (err) {
        console.error('Part C history load error:', err);
    }
}

// ==========================================
// PART D: FLYWEIGHT PATTERN (STREET LIGHTS)
// ==========================================
function initPartD() {
    const btnDeploy = document.getElementById('btnDeployFleet');
    if (!btnDeploy) return;

    btnDeploy.addEventListener('click', async () => {
        const count = parseInt(document.getElementById('numFleetCount').value) || 10000;
        const highmastPercent = parseInt(document.getElementById('numHighmastPercent').value) || 50;

        btnDeploy.disabled = true;
        btnDeploy.innerHTML = `<svg class="tab-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 6v6l4 2"/></svg> Allocating Shared Flyweights...`;

        try {
            const res = await fetch('/api/luminaire/deploy', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ count, highmastPercent })
            });
            const data = await res.json();
            renderFleetResults(data);
        } catch (err) {
            alert('Failed to deploy luminaires: ' + err.message);
        } finally {
            btnDeploy.disabled = false;
            btnDeploy.innerHTML = `<svg class="tab-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 18h6"/><path d="M10 22h4"/><path d="M15.09 14c.18-.98.65-1.74 1.41-2.5A4.65 4.65 0 0 0 18 8 6 6 0 0 0 6 8c0 1 .23 2.23 1.5 3.5A4.61 4.61 0 0 1 8.91 14"/></svg> Deploy Smart Streetlight Network`;
        }
    });

    // Initial load
    btnDeploy.click();
}

function renderFleetResults(data) {
    document.getElementById('lblTotalNodes').innerText = data.totalNodes.toLocaleString();
    document.getElementById('lblFlyweightPool').innerText = data.flyweightPoolCount;
    document.getElementById('lblRamSavings').innerText = data.savingsPercent + '%';
    document.getElementById('lblMemoryWithout').innerText = (data.bytesWithoutFlyweight / 1024).toFixed(1) + ' KB';
    document.getElementById('lblMemoryWith').innerText = (data.bytesWithFlyweight / 1024).toFixed(1) + ' KB';

    const grid = document.getElementById('poleSamplesGrid');
    grid.innerHTML = '';
    if (data.sampleLuminaires) {
        data.sampleLuminaires.forEach(sl => {
            const div = document.createElement('div');
            div.className = `pole-card ${sl.illuminated ? 'lit' : ''}`;

            const svgIcon = sl.illuminated
                ? `<svg class="pole-icon lit-icon" viewBox="0 0 24 24"><path d="M9 18h6"/><path d="M10 22h4"/><path d="M15.09 14c.18-.98.65-1.74 1.41-2.5A4.65 4.65 0 0 0 18 8 6 6 0 0 0 6 8c0 1 .23 2.23 1.5 3.5A4.61 4.61 0 0 1 8.91 14"/></svg>`
                : `<svg class="pole-icon off-icon" viewBox="0 0 24 24"><path d="M9 18h6"/><path d="M10 22h4"/><path d="m2 2 20 20"/><path d="M8.91 14c-.5.76-.97 1.52-1.41 2.5"/><path d="M6 8a6 6 0 0 1 10.6-3.8"/></svg>`;

            div.innerHTML = `
                <div class="pole-number">${escapeHtml(sl.poleNumber)}</div>
                ${svgIcon}
                <div class="pole-specs">${sl.model}</div>
                <div style="font-size:0.7rem; color:var(--accent-amber); font-weight:600;">${sl.wattage}W LED</div>
                <div style="font-size:0.68rem; margin-top:0.25rem; font-weight:600; color:${sl.illuminated ? 'var(--accent-amber)' : 'var(--text-muted)'};">
                    ${sl.illuminated ? 'ACTIVE' : 'STANDBY'}
                </div>
            `;
            grid.appendChild(div);
        });
    }
}

// ==========================================
// PART E: INTERPRETER PATTERN (CITY POLICIES)
// ==========================================
function initPartE() {
    const btnEval = document.getElementById('btnEvaluatePolicy');
    if (!btnEval) return;

    btnEval.addEventListener('click', async () => {
        const policyMode = document.getElementById('selPolicyMode').value;
        const currentHour = parseInt(document.getElementById('rngCurrentHour').value);
        const noiseDecibels = parseFloat(document.getElementById('rngNoise').value);
        const parkedHours = parseInt(document.getElementById('rngParkedHours').value);

        try {
            const res = await fetch('/api/policy/evaluate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ policyMode, currentHour, noiseDecibels, parkedHours })
            });
            const data = await res.json();
            renderPolicyResult(data);
        } catch (err) {
            alert('Failed to evaluate policy: ' + err.message);
        }
    });

    // Update range labels
    document.getElementById('rngCurrentHour').addEventListener('input', (e) => {
        document.getElementById('lblCurrentHour').innerText = e.target.value.padStart(2, '0') + ':00 hrs';
    });
    document.getElementById('rngNoise').addEventListener('input', (e) => {
        document.getElementById('lblNoise').innerText = e.target.value + ' dB';
    });
    document.getElementById('rngParkedHours').addEventListener('input', (e) => {
        document.getElementById('lblParkedHours').innerText = e.target.value + ' Hours';
    });

    // Initial run
    btnEval.click();
}

function renderPolicyResult(data) {
    document.getElementById('policyRuleTitle').innerText = data.ruleTitle;
    document.getElementById('policyAstExpr').innerText = data.astRepresentation;

    const verdictBox = document.getElementById('policyVerdictBox');
    if (data.compliant) {
        verdictBox.style.borderLeftColor = 'var(--accent-green)';
        verdictBox.innerHTML = `
            <div style="color:var(--accent-green); font-size:1.1rem; font-weight:700;">${data.verdictLabel}</div>
            <div style="font-size:0.85rem; color:var(--text-secondary); margin-top:0.2rem;">${data.enforcementAction}</div>
        `;
    } else {
        verdictBox.style.borderLeftColor = 'var(--accent-red)';
        verdictBox.innerHTML = `
            <div style="color:var(--accent-red); font-size:1.1rem; font-weight:700;">${data.verdictLabel}</div>
            <div style="font-size:0.85rem; color:var(--text-secondary); margin-top:0.2rem;">${data.enforcementAction}</div>
        `;
    }
}

function escapeHtml(text) {
    if (!text) return '';
    return text.toString()
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}
