# Data Integration using RabbitMQ (Java + JTables)

## Overview
This project addresses the challenge of synchronizing distributed databases between a Head Office (HO) and multiple Branch Offices (BOs) with intermittent internet connectivity. The solution leverages RabbitMQ message queues to facilitate data synchronization between the offices.

## Problem Statement
In scenarios where internet connectivity is limited or intermittent, synchronizing databases across distributed locations becomes challenging. The project assumes the following constraints:
- A central Head Office (HO) and two Branch Offices (BOs) for sales.
- Physically separated offices with varying internet connectivity, possibly limited to a few hours per day.

## Solution Approach
The proposed solution involves creating a distributed application to synchronize databases using RabbitMQ queues. Each branch office manages its database independently and synchronizes data with the Head Office.

### Key Components
- **Java with JDBC Connector**: Utilized for MySQL databases to interact with sales data.
- **RabbitMQ Message Queues**: Facilitates communication between branch offices and the head office.
- **Migration Scripts**: Recorded for each database operation and transmitted to RabbitMQ for synchronization.
- **Database Monitoring Application**: Subscribed to RabbitMQ to receive migration scripts, execute them on the Head Office database, and send acknowledgments.

### Workflow
1. **Migration Script Generation**: Every database operation in the branch offices triggers the creation of a migration script.
2. **Transmission via RabbitMQ**: Migration scripts are sent to the RabbitMQ server.
3. **Execution at Head Office**: The Head Office database monitoring application receives migration scripts, executes them, and sends acknowledgments.
4. **Cleanup and Monitoring**: Upon acknowledgment receipt, the branch office deletes the migration script from RabbitMQ.

## Setup Instructions
To set up and run the application, follow these steps:

1. **Database Configuration**:
   - Set up MySQL databases for the Head Office and Branch Offices (headofficetunis, branchofficesousse, branchofficesfax)
   - All databases have the same structure : one table "sales" that has the following columns : (int product_id, String date, String region, String product_name, int quantity, float cost, float tax, float total_sales)
   - Ensure proper JDBC connectivity in the Java application via running the TestCRUD classes.

2. **RabbitMQ Setup**:
   - Install and configure RabbitMQ server.

3. **Execution**:
   - Start the application processes at both Branch Offices and the Head Office.
   - Monitor synchronization progress and database consistency.

## Conclusion
By leveraging RabbitMQ message queues and Java-based database interactions, this project offers a robust solution for synchronizing data across distributed offices with intermittent internet connectivity. The application architecture ensures reliable communication and data consistency between the Head Office and Branch Offices.
