## Requirements

R1: The parking lot should have the capacity to park N vehicles.

R2: The three different types of parking spots are compact, large, and motorcycle.

R3: The parking lot should have multiple entrance and exit points.

R4: Four types of vehicles should be allowed to park in the parking lot, which are as follows:
- Car
- Truck
- Van
- Motorcycle

R5: The parking lot should have a display board that shows free parking spots for each parking spot type.

R6: The system should not allow more vehicles in the parking lot if the maximum capacity is reached.

R7: Customers should be able to collect a parking ticket from the entrance and pay at the exit.

R8: The customer can pay for the ticket at the automated exit panel.

R9: The payment should be calculated at an hourly rate.

R10: Payment can be made using either a debit card, or UPI.

## Future work
1. **New requirement**: Add support for multiple floors.
2. **New requirement**: Add support for closest spot assignment strategy. Current strategy is to assign the first available spot of a compatible type. 
3. **Design improvement**: Ideally, strategy pattern should be used for assignment strategy.
4. **Design improvement**: Implementation of Display should be ideally be done using Observer Pattern. Whenever available spots changes, display should automatically get updated.
5. **Design improvement + New requirement**: Currently, vehicle class is implemented using just enums. There was no need for separate classes for each type because none of them needed separate special methods for their types. But, if we plan to add support for `Electric` vehicle type, we might need separate class if we also plan to add charging outlet to charge the vehicle.