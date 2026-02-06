1️⃣ Entities and Relationships
1. Movie

Attributes: id, title, language, genre, durationMinutes

Relationships:

1 Movie → many Shows (OneToMany)

2. Theatre

Attributes: id, name, city, address, active (boolean)

Relationships:

1 Theatre → many Screens (OneToMany)

Active theatres are used for discounts

3. Screen

Attributes: id, name, totalSeats, format (e.g., IMAX)

Relationships:

Many Screens → 1 Theatre (ManyToOne)

1 Screen → many Seats (OneToMany)

1 Screen → many Shows (OneToMany)

4. Seat

Attributes: id, seatNumber (A1, A2...), type (PREMIUM / REGULAR)

Relationships:

Many Seats → 1 Screen (ManyToOne)

Seats are used to generate ShowSeats for each Show

5. Show

Attributes: id, showDate, showTime, basePrice

Relationships:

Many Shows → 1 Movie (ManyToOne)

Many Shows → 1 Screen (ManyToOne)

1 Show → many ShowSeats (OneToMany)

6. ShowSeat

Attributes: id, booked (boolean)

Relationships:

Many ShowSeats → 1 Show (ManyToOne)

Many ShowSeats → 1 Seat (ManyToOne)

Purpose: Tracks seat availability for each Show

7. User

Attributes: id, name, email

Relationships:

1 User → many Bookings (OneToMany)

8. Booking

Attributes: id, totalSeats, totalAmount, bookingTime

Relationships:

Many Bookings → 1 Show (ManyToOne)

Many Bookings → 1 User (ManyToOne)