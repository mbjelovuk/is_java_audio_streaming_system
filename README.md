# Audio Streaming System

**University:** University of Belgrade, School of Electrical Engineering
**Course:** Information Systems 1
**Year:** 2024/2025

## Overview

Distributed system that allows users to upload, listen to, rate and collect audio recordings.  
Users can subscribe to monthly packages. The system is split into a client, central server and three independent subsystems that communicate via JMS.

### Main entities

| Entity              | Description                                      |
|---------------------|--------------------------------------------------|
| User                | Name, email, year of birth, gender, place        |
| Place               | City / town name                                 |
| Audio recording     | Title, duration, owner, upload datetime          |
| Category            | Many-to-many relation with audio                 |
| Package             | Monthly subscription price                       |
| Subscription        | One active subscription per user                 |
| Listening history   | Start time, starting second, seconds listened    |
| Rating              | Score 1–5 + timestamp                            |
| Favorites           | Unique list of favorite audio per user           |

## Architecture
Client (Java SE)
│ REST

Central Server
│ JMS
- Subsystem 1  (Users + Places)
- Subsystem 2  (Audio + Categories)
- Subsystem 3  (Packages, Subscriptions, Listening, Ratings, Favorites)

--------------------------------------------------------------------------------------------------
-- **Client** – console application that sends REST requests

-- **Central Server** – receives REST requests and routes them to the correct subsystem via JMS

-- **Subsystems** – communicate only through JMS, share one MySQL database
  
