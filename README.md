# TenTen

## Subject : Computer simulator
We want you to build a computer simulator that supports executing:

```
def print_tenten
print(multiply(101, 10))
end
print(1009)
print_tenten()

// result
# 1009 
# 1010
```

### Instructions
- `MULT`: Pop the 2 arguments from the stack, multiply them and push the result back to the stack 
- `CALL addr`: Set the program counter (PC) to `addr`
- `RET`: Pop address from stack and set PC to address
- `STOP`: Exit the program
- `PRINT`: Pop value from stack and print it 
- `PUSH arg`: Push argument to the stack

### The code should execute against:

```
PRINT_TENTEN_BEGIN = 50
MAIN_BEGIN = 0
def main

# Create new computer with a stack of 100 addresses
computer = Computer.new(100)

# Instructions for the print_tenten function
computer.set_address(PRINT_TENTEN_BEGIN).insert("MULT").insert("PRINT").insert("RET")

# The start of the main function
computer.set_address(MAIN_BEGIN).insert("PUSH", 1009).insert("PRINT")

# Return address for when print_tenten function finishes
computer.insert("PUSH", 6)

# Setup arguments and call print_tenten
computer.insert("PUSH", 101).insert("PUSH", 10).insert("CALL", PRINT_TENTEN_BEGIN)

# Stop the program
computer.insert("STOP")
computer.set_address(MAIN_BEGIN).execute()

end
main()
```

### Stack

This is what the stack should look like before the program gets executed.


## Requirements
- Android min sdk 19 - KitKat
- Kotlin

## How does it work?

This project was developed with Android MVP architecture:

- Models : 
    - Computer : represents the computer
    - StackItem : represents a stack  
- Contract : Interfaces that the View and the Presenter will follow
- View : In this case, it is the MainActivity which have only logic of showing data
- Presenter : Has all the technical logic of the feature and will run the computer

This project could also be developed with the Android MVVM architecture. But, I decided to do this subject in MVP because we don't need observe data all the time.
If you have question about it, be pleased to ask.


## TODO
- Test all the limits of the feature
- Show stack step by step
- Add info and error message in string.xml