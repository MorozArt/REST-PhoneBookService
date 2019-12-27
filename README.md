# REST-PhoneBookService

  # Get Methods:
  
   1. ServerAddress/users?name(not required) - if name is set return users whose names contain this parameter, else return all users
   2. ServerAddress/users/{id} - return user by id
   3. ServerAddress/users/{id}/phoneBook - return users(by id) phone book
   4. ServerAddress/users/{id}/phoneBook/contacts?contactName - return all contacts whose names equals contactName parameter
   5. ServerAddress/users/{id}/phoneBook/phone(RequestBody PhoneNumber) - return contact for phone number
        PhoneNumber JSON example: 
          {
            "countryCode": 7,
            "operatorCode": 961,
            "number": 2649160
          }
  # Post Methods:
  
   1. ServerAddress/users?name - create new user with name equals "name" parameter
   2. ServerAddress/users/{id}/phoneBook?contactName(RequestBody PhoneNumber) - add users(by id) phone book new contact with name=contactName and phone number from RequestBody
          
  # Put Methods:
   1. ServerAddress/users/{id}?name - rename user(by id)
   2. ServerAddress/users/{id}/phoneBook/phone?newContactName(not required) (RequestBody PhoneNumber[2]) - change phone number for contact by PhoneNumber[0] to phone number by PhoneNumber[1] and rename this contact if newContactName is set. If PhoneNumber[] array length != 2 return BAD_REQUEST status.
   3. ServerAddress/users/{id}/phoneBook/contacts/{contactName}?newContactName - rename all contacts whose names equals contactName parameter to newContactName
   
  # Delete Methods:
   1. ServerAddress/users/{id} - delete user(by id)
   2. ServerAddress/users/{id}/phoneBook(RequestBody PhoneNumber) - delete contact(by PhoneNumber) from user phone book
   3. ServerAddress/users/{id}/phoneBook/contacts?contactName - delete all contacts whose names equals contactName parameter from user phone book
