# employee-management-system
Employee Management System(Server Layer)

# 🔁 Transaction Management in Spring Boot
### 🛒 1. Order Processing Transaction
@Transactional
public void placeOrder(Order order) {
orderRepo.save(order);              // Step 1: Save order
productRepo.updateStock(order);     // Step 2: Reduce stock
paymentRepo.processPayment(order);  // Step 3: Payment (may fail)
}

### 💸 2. Money Transfer Transaction
@Transactional
public void transferMoney(Account from, Account to, double amount) {
accountRepo.debit(from, amount);   // Step 1: Deduct money
accountRepo.credit(to, amount);    // Step 2: Add money (may fail)
}

### 🎟️ 3. Ticket Booking Transaction
@Transactional
public void bookTicket(User user, Show show) {
seatRepo.reserveSeat(show);   // Step 1: Reserve seat
paymentRepo.pay(user);        // Step 2: Payment (may fail)
}

## ERROR:
ResourceNotFoundException: Employee is not exist with given id: 2