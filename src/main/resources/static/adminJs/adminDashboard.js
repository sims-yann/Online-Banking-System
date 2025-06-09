document.addEventListener("DOMContentLoaded", function () {
    // Fetch statistics
    // fetch('/api/admin/statistics')
    //     .then(res => res.json())
    //     .then(data => {
    //         document.getElementById('total-users').textContent = data.totalUsers;
    //         document.getElementById('active-users').textContent = data.activeUsers;
    //         document.getElementById('inactive-users').textContent = data.inactiveUsers;
    //
    //         document.getElementById('total-accounts').textContent = data.totalAccounts;
    //         document.getElementById('active-accounts').textContent = data.activeAccounts;
    //         document.getElementById('suspended-accounts').textContent = data.suspendedAccounts;
    //
    //         document.getElementById('total-transactions').textContent = data.totalTransactions;
    //         document.getElementById('pending-transactions').textContent = data.pendingTransactions;
    //         document.getElementById('failed-transactions').textContent = data.failedTransactions;
    //     });

    // Fetch recent users
    // fetch('/api/admin/recent-users')
    //     .then(res => res.json())
    //     .then(users => {
    //         const list = document.getElementById('recent-users-list');
    //         users.forEach(user => {
    //             const li = document.createElement('li');
    //             li.className = 'list-group-item d-flex justify-content-between align-items-center';
    //             let badgeClass = user.status === "active" ? "badge-success" : "badge-danger";
    //             li.innerHTML = `
    //                 <div>
    //                     <i class="bi bi-person-circle me-2"></i>
    //                     <strong>${user.name}</strong><br>
    //                     <small>${user.email}</small>
    //                 </div>
    //                 <span class="badge ${badgeClass}">${user.status}</span>
    //             `;
    //             list.appendChild(li);
    //         });
    //     });

    // Fetch recent transactions
    // fetch('/api/admin/recent-transactions')
    //     .then(res => res.json())
    //     .then(transactions => {
    //         const list = document.getElementById('recent-transactions-list');
    //         transactions.forEach(tx => {
    //             const li = document.createElement('li');
    //             li.className = 'list-group-item d-flex justify-content-between align-items-center';
    //             let badgeClass = "badge-success";
    //             if (tx.status === "pending") badgeClass = "badge-warning";
    //             if (tx.status === "failed") badgeClass = "badge-danger";
    //             li.innerHTML = `
    //                 <div>
    //                     <strong>${tx.type}</strong><br>
    //                     <small>${tx.date}</small>
    //                 </div>
    //                 <div>
    //                     <span class="fw-bold">$${tx.amount.toLocaleString(undefined, {minimumFractionDigits: 2})}</span><br>
    //                     <span class="badge ${badgeClass}">${tx.status}</span>
    //                 </div>
    //             `;
    //             list.appendChild(li);
    //         });
    //     });
});