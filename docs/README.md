# Elderly in your Hood

## Features

### Storing of elderly details

Allows the user (you!!) to store information about an elderly into the
given application. Information includes: medical appointments, medicine, records, etc.
(View Usage for more details)

### Viewing of elderly details

With information about each elderly being added into the application, you can proceed
to view them now.

## Usage

### `addelderly` - Adds an elderly into the application

The given elderly username will be added. Username is used in the case
where some elderly may have the same name. The elderly will get to choose
his username. 

Example of usage:

`addelderly n/johntan123`

Expected outcome:

Elderly added message will be shown. 

```
Elderly has been added!
```

### `addmed` - Adds a medicine to an elderly

The medicine name and its frequency to be taken will be added to the elderly's
given username. 

Example of usage:

`addmed n/johntan123 m/paracetamol f/once a day`

Expected outcome:

Medicine added message will be shown.

```
Medicine has been added!
```

### `viewmed` - Views medicine required by the elderly

The medicine name and its frequency to be taken for all medicine allocated to
that elderly will be shown by the application. 

Example of usage:

`viewmed n/johntan123`

Expected outcome:

List of all medicine allocated to that elderly will be shown.

```
Medicine of johntan123 are shown below:
  1.Medicine Name: paracetemol, Frequency: once a day
```

### `deletemed` - Deletes medicine details of elderly that has been stored

The user will provide the medicine name and elderly username that the medicine information is to be deleted.

Example of usage:

`deletemed u/johntan123 m/paracetemol`

Expected outcome:

Medicine details deleted message will be shown.

```
These medicine details are now deleted:
Medicine Name: paracetemol, Frequency: once a day
```


### `addappt` - Adds an appointment to an elderly

Details of the elderly's appointment will be added to the application.
Specifically, the details are location, date, time and purpose. 
Purpose is an optional parameter that defaults to `general checkup` if
not specified by user.

Example of usage:

`addappt n/johntan123 l/khoo teck puat hospital d/02012021 t/1000`
`addappt n/johntan123 l/khoo teck puat hospital d/05032021 t/1500 p/eye checkup`

Expected outcome:

Appointment added message will be shown.

```
Appointment has been added!
```

### `viewappt` - Views upcoming appointments that the elderly has

Appointment details for all appointments of the given elderly will be shown by the application. 

Example of usage:

`viewappt n/johntan123`

Expected outcome:

List of all appointment allocated to that elderly will be shown.

```
Appointments of johntan123 are shown below:
  1.Location: khoo teck puat hospital, Date: 02012021, Time: 1000, Purpose of Visit: general checkup
  2.Location: khoo teck puat hospital, Date: 05032021, Time: 1500, Purpose of Visit: eye checkup
```

### `deleteappt` - Deletes appointment details of elderly that has been stored

The user will provide the date and time of appointment and the elderly username that the appointment information is
to be deleted.

Example of usage:

`deleteappt u/johntan123 d/02012021 t/1000`

Expected outcome:

Appointment details deleted message will be shown.

```
These appointment details are now deleted:
Location: khoo teck puat hospital, Date: 02012021, Time: 1000, Purpose of Visit: general checkup
```

### `addnok` - Adds details of Next-Of-Kin to an elderly

Details of the elderly's Next-Of-Kin will be added to the application.
Specifically, the details are name, phone number, email, address and relationship.

Example of usage:

`addnok n/johntan123 k/tony lim p/98765432 e/tonylim@gmail.com a/123 Yishun Street r/son`


Expected outcome:

Next-Of-kin added message will be shown.

```
Next-Of-Kin has been added!
```

### `viewnok` - Views details of Next-Of-Kin that the elderly has

Next-Of-Kin details of the given elderly will be shown by the application.

Example of usage:

`viewnok n/johntan123`

Expected outcome:

Next-Of-Kin details allocated to that elderly will be shown.

```
Details of johntan123 Next-of-Kin are shown below:
[NOK Name: tony lim, NOK Phone Number: 98765432, NOK Email: tonylim@gmail.com, NOK Aaddress: 123 Yishun Street, 
Relationship: son]
```

### `deletenok` - Deletes Next-of-Kin details of elderly that has been stored

The user will provide the elderly username and the name of Next-of-Kin, which the information is to be deleted.

Example of usage:

`deletenok u/johntan123 n/tony lim`

Expected outcome:

Next-ofKin details deleted message will be shown.

```
These details are now deleted:
NOK Name: tony lim, NOK HP: 98765432, NOK Email: tonylim@gmail.com, NOK Address: 123 Yishun Street, Relationship: son
```

### `addrec` - Adds record details to an elderly

Details of the elderly will be added to the application.
Specifically, the details are name, phone number and address.

Example of usage:

`addrec n/johntan123 p/93456781 a/123 Yishun Street`


Expected outcome:

Record added message will be shown.

```
Record of elderly has been added!
```

### `viewrec` - Views details of elderly that has been stored

Next-Of-Kin details for all appointments of the given elderly will be shown by the application.

Example of usage:

`viewrec n/johntan123`

Expected outcome:

Next-Of-Kin details allocated to that elderly will be shown.

```
Record of johntan123 is shown below:
[Elderly HP: 93456781, Elderly Address: 123 Yishun Street]
```

### `bye` - Terminates the application

Application will be terminated

Example of usage:

`bye`

Expected outcome:

Bye message will be printed out.

```
Bye command has been recognized. Goodbye!
```

Useful links:
* [User Guide](UserGuide.md)
* [Developer Guide](DeveloperGuide.md)
* [About Us](AboutUs.md)
