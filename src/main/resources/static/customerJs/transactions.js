// document.addEventListener('DOMContentLoaded', function() {
//     // Handle transaction details modal
//     const modal = document.getElementById('transaction-details-modal');
//     const closeModalBtn = document.querySelector('.close-modal');
//     const viewDetailsButtons = document.querySelectorAll('.view-details');
//
//     // Close modal when clicking the close button
//     if (closeModalBtn) {
//         closeModalBtn.addEventListener('click', function() {
//             modal.style.display = 'none';
//         });
//     }
//
//     // Close modal when clicking outside of it
//     window.addEventListener('click', function(event) {
//         if (event.target === modal) {
//             modal.style.display = 'none';
//         }
//     });
//
//     // Open modal when clicking view details button
//     viewDetailsButtons.forEach(button => {
//         button.addEventListener('click', function() {
//             const transactionId = this.getAttribute('data-id');
//             fetchTransactionDetails(transactionId);
//         });
//     });
//
//     // Function to fetch transaction details
//     function fetchTransactionDetails(transactionId) {
//         const modalContent = document.getElementById('transaction-details-content');
//
//         // Show loading state
//         modalContent.innerHTML = `
//             <div class="transaction-detail-header">
//                 <div>
//                     <h3>Loading Transaction Details...</h3>
//                 </div>
//             </div>
//         `;
//
//         modal.style.display = 'block';
//
//         // Fetch transaction details from the server
//         fetch(`/customer/transactions/${transactionId}`)
//             .then(response => {
//                 if (!response.ok) {
//                     throw new Error('Failed to fetch transaction details');
//                 }
//                 return response.json();
//             })
//             .then(transaction => {
//                 // Format date
//                 const createdAt = new Date(transaction.createdAt);
//                 const formattedDate = createdAt.toLocaleDateString('en-US', {
//                     year: 'numeric',
//                     month: 'long',
//                     day: 'numeric'
//                 });
//                 const formattedTime = createdAt.toLocaleTimeString('en-US', {
//                     hour: '2-digit',
//                     minute: '2-digit'
//                 });
//
//                 // Determine if it's an outgoing or incoming transaction
//                 const transactionDirection = transaction.transactionType === 'WITHDRAW' ||
//                                             (transaction.transactionType === 'TRANSFER' && transaction.fromAccount)
//                                             ? 'outgoing' : 'incoming';
//
//                 // Build the modal content
//                 modalContent.innerHTML = `
//                     <div class="transaction-detail-header">
//                         <div>
//                             <h3>${transaction.transactionType.charAt(0) + transaction.transactionType.slice(1).toLowerCase()} Transaction</h3>
//                             <p class="transaction-subtitle">Transaction ID: ${transaction.id}</p>
//                         </div>
//                         <div class="transaction-amount ${transactionDirection === 'outgoing' ? 'amount-negative' : 'amount-positive'}">
//                             ${transactionDirection === 'outgoing' ? '-' : '+'} ${transaction.amount}
//                         </div>
//                     </div>
//
//                     <div class="transaction-details-grid">
//                         <div class="transaction-detail-item">
//                             <div class="transaction-detail-label">Date & Time</div>
//                             <div class="transaction-detail-value">${formattedDate} at ${formattedTime}</div>
//                         </div>
//                         <div class="transaction-detail-item">
//                             <div class="transaction-detail-label">Status</div>
//                             <div class="transaction-detail-value">
//                                 <span class="badge ${transaction.transactionStatus === 'COMPLETED' ? 'badge-default' :
//                                                     (transaction.transactionStatus === 'PENDING' ? 'badge-secondary' : 'badge-destructive')}">
//                                     ${transaction.transactionStatus}
//                                 </span>
//                             </div>
//                         </div>
//                         <div class="transaction-detail-item">
//                             <div class="transaction-detail-label">From Account</div>
//                             <div class="transaction-detail-value">${transaction.fromAccount ? transaction.fromAccount.accountNumber : 'N/A'}</div>
//                         </div>
//                         <div class="transaction-detail-item">
//                             <div class="transaction-detail-label">To Account</div>
//                             <div class="transaction-detail-value">${transaction.toAccount ? transaction.toAccount.accountNumber : 'N/A'}</div>
//                         </div>
//                     </div>
//
//                     ${transaction.description ? `
//                     <div class="transaction-details-notes">
//                         <h4>Description</h4>
//                         <p>${transaction.description}</p>
//                     </div>
//                     ` : ''}
//                 `;
//             })
//             .catch(error => {
//                 console.error('Error fetching transaction details:', error);
//                 modalContent.innerHTML = `
//                     <div class="transaction-detail-header">
//                         <div>
//                             <h3>Error</h3>
//                         </div>
//                     </div>
//                     <p>Failed to load transaction details. Please try again later.</p>
//                 `;
//             });
//     }
//
//     // Handle export button
//     const exportButton = document.getElementById('export-button');
//     if (exportButton) {
//         exportButton.addEventListener('click', function() {
//             alert('Export functionality would be implemented here.');
//         });
//     }
// });
