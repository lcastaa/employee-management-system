# Employee Management System (EMS)

## Overview
This application was created to simulate the functionality of Amazon's A to Z app, which Amazon employees use to manage punches, time cards, scheduling, pay, and HR-related inquiries.

## Features
- Login / Register
- View Time Cards
- Submit Time Stamps
- Submit HR Requests
- Submit Time Off Requests
- View Pretax Pay and Hourly Rate
- View Employee Profile

## API Endpoints

| Endpoint             | Method | Description                                     | Request Body                    | Response                                                                                      |
|----------------------|--------|-------------------------------------------------|---------------------------------|------------------------------------------------------------------------------------------------|
| `/api/v1/authenticate/register` | POST   | Registers a new employee                      | JSON representing employee details | Result of the registration process, including success or error messages                      |
| `/api/v1/authenticate/login`    | POST   | Logs in an employee using credentials         | JSON containing login credentials | Authentication result, including an access token if successful                                 |
| `/api/v1/hr`                    | POST   | Creates a new HR ticket                        | JSON representing HR ticket details | Result of the HR ticket creation process, including success or error messages                   |
| `/api/v1/hr`                    | GET    | Retrieves a list of HR tickets                | `employeeId` (Integer) and `isResolved` (Boolean) as query parameters | List of HR tickets matching the specified criteria                                        |
| `/api/v1/punch`                 | POST   | Creates a new punch entry                     | JSON representing punch details using `PunchDto` structure | Result of the punch creation process, including success or error messages                    |
| `/api/v1/timecard`             | POST   | Creates a new time card entry for an employee | JSON representing time card details using `TimeCardDto` structure | Result of the time card creation process, including success or error messages                  |
| `/api/v1/timecard`             | PUT    | Query time cards based on specific criteria   | JSON containing query parameters using `QueryDto` structure | Time cards that match the specified query criteria                                            |

#### Query Criteria

- `thisweek`: Query time cards within a specified date range.
- `bymonth`: Query time cards for a specific month.
- `byyear`: Query time cards for a specific year.
- `bymonthyear`: Query time cards for a specific month and year.

#### Query Parameters

- `query`: A comma-separated string that defines the query criteria. The format is `[employeeId, criteria, value]`.

  - Example 1: `[1, thisweek, start_date, end_date]` queries time cards for employee 1 within the specified date range.
  - Example 2: `[2, bymonth, month]` retrieves time cards for employee 2 in the specified month.
  - Example 3: `[3, byyear, year]` retrieves time cards for employee 3 in the specified year.
  - Example 4: `[4, bymonthyear, month, year]` retrieves time cards for employee 4 in the specified month and year.

#### Example Query Using QueryDto

To query time cards using the `QueryDto` structure, you can provide the following example in the request body:

```json
{
  "query": "[1, employeeId, criteria, value]"
}


