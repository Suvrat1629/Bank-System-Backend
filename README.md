# Bank-System-Bank-end

This is a Spring Boot-based backend system for a banking application. The system provides several services for managing customer accounts, transactions, cards, and more. The services are designed to be secure, scalable, and modular, allowing easy integration and management of bank operations.

## Services Overview

### 1. Customer Service 
Manages customer information and actions related to customer profiles and account management.
#### Methods:
- **Register Customer**: Adds a new customer after validating all required information.
- **Update KYC Details**: Updates customer KYC (Know Your Customer) details such as ID proofs and address.
- **Get Customer Profile**: Retrieves the customer profile with linked accounts.
- **Deactivate Customer Account**: Marks a customer account as inactive after proper validation.

### 2. Account Service 
Handles operations related to bank accounts, including account creation, balance checks, and account management.
#### Methods:
- **Create Account**: Opens a new savings or current account for a customer.
- **Check Account Balance**: Retrieves the current balance of a given account.
- **Update Account Type**: Changes an account type from Savings to Current or vice versa, if permitted.
- **Calculate Interest**: Calculates interest for savings accounts based on the account balance and rate.
- **Link Multiple Accounts**: Links multiple accounts to a single customer for ease of management.

### 3. Transaction Service 
Manages financial transactions like transfers, deposits, and withdrawals.
#### Methods:
- **Transfer Funds**: Allows secure transfer of money between accounts (within the bank or external).
- **Deposit Money**: Facilitates the deposit of money into an account.
- **Withdraw Money**: Handles cash withdrawal while checking balance and withdrawal limits.
- **Generate Mini Statement**: Provides the last 5 or 10 transactions for an account.
- **Schedule Recurring Payments**: Automates recurring transactions (e.g., rent, loan EMIs).

### 4. Admin Service 
Provides administrative capabilities to manage accounts, transactions, and customer feedback.
#### Methods:
- **Block Account**: Freezes an account in case of fraud or suspicious activity.
- **Monitor Transactions**: Provides insights into high-value or suspicious transactions.
- **Approve Account Creation**: Manages the approval of new accounts.
- **Manage Interest Rates**: Updates interest rates for various account types or loans.
- **View Customer Complaints**: Accesses customer feedback and complaints for resolution.

### 5. Card Management Service 
Handles operations related to debit and credit cards, including issuance, limits, and transactions.
#### Methods:
- **Request New Card**: Processes requests for debit or credit cards.
- **Block Lost Card**: Blocks a card immediately after it is reported lost.
- **Set Card Limits**: Allows customers to set daily limits for ATM withdrawals or online transactions.
- **View Card Transactions**: Retrieves transaction history linked to a card.
- **Change PIN**: Updates the card's PIN securely.

## Technologies Used
- **Spring Boot**: For building the backend system.
- **Spring Data JPA**: For interacting with the database.
- **MySQL**: For the database.
- **Spring Security**: For securing the application and authentication.

## Setup and Installation

### Prerequisites
- JDK 11 or higher
- Maven or Gradle
- IDE (e.g., IntelliJ IDEA or Eclipse)

### Steps to Run the Application
1. Clone the repository to your local machine:
    ```bash
    git clone https://github.com/your-username/Bank-System-Bank-end.git
    ```

2. Navigate to the project directory:
    ```bash
    cd Bank-System-Bank-end
    ```

3. Build the project using Maven or Gradle:
    - Maven:
      ```bash
      mvn clean install
      ```
    - Gradle:
      ```bash
      gradle build
      ```

4. Run the application:
    - Maven:
      ```bash
      mvn spring-boot:run
      ```
    - Gradle:
      ```bash
      gradle bootRun
      ```

5. Access the application at `http://localhost:8080`.

## Endpoints

Here are some example endpoints for interacting with the services:

### Customer Service
- `POST /customers/register` - Register a new customer.
- `PUT /customers/kyc/{customerId}` - Update KYC details.
- `GET /customers/{customerId}` - Get customer profile.
- `PUT /customers/deactivate/{customerId}` - Deactivate a customer account.

### Account Service
- `POST /accounts/create` - Create a new account.
- `GET /accounts/{accountId}/balance` - Check account balance.
- `PUT /accounts/{accountId}/type` - Update account type.
- `GET /accounts/{accountId}/interest` - Calculate interest for savings account.
- `POST /accounts/{accountId}/link` - Link multiple accounts.

### Transaction Service
- `POST /transactions/transfer` - Transfer funds between accounts.
- `POST /transactions/deposit` - Deposit money into an account.
- `POST /transactions/withdraw` - Withdraw money from an account.
- `GET /transactions/{accountId}/ministatement` - Generate mini statement for an account.
- `POST /transactions/schedule` - Schedule recurring payments.

### Admin Service
- `POST /admin/block/{accountId}` - Block an account.
- `GET /admin/transactions/monitor` - Monitor high-value or suspicious transactions.
- `POST /admin/approve-account` - Approve new account creation.
- `PUT /admin/interest-rates` - Manage interest rates for accounts.
- `GET /admin/complaints` - View customer complaints.

### Card Management Service
- `POST /cards/request` - Request a new debit/credit card.
- `PUT /cards/block/{cardId}` - Block a lost card.
- `PUT /cards/{cardId}/limits` - Set card limits.
- `GET /cards/{cardId}/transactions` - View card transactions.
- `PUT /cards/{cardId}/pin` - Change card PIN.
