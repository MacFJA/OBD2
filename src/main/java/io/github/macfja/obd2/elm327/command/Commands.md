# Commands of ELM327

 Code                | Implemented | Type    | Short description
---------------------|-------------|---------|-------------------
 `@1`                | **YES**     | General | Display the device description
 `@2`                | **YES**     | General | Display the device identifier
 `@3 cccccccccccc`   | _NO_        | General | Store the device identifier
 `<CR>`              | _NO_        | General | Repeat the last command
 `AL`                | **YES**     | OBD     | Allow Long (>7 byte) messages
 `AMC`               | **YES**     | OBD     | Display Activity Monitor Count
 `AMT hh`            | **YES**     | OBD     | Set the Activity Mon Timeout to hh
 `AR`                | **YES**     | OBD     | Automatic Receive
 `AT0`               | **YES**     | OBD     | Adaptive Timing Off
 `AT1`               | **YES**     | OBD     | Adaptive Timing Auto1
 `AT2`               | **YES**     | OBD     | Adaptive Timing Auto2
 `BD`                | **YES**     | OBD     | Perform a Buffer Dump
 `BI`                | **YES**     | OBD     | Bypass the Initialization sequence
 `BRD hh`            | **YES**     | General | Try Baud rate Divisor hh
 `BRT hh`            | **YES**     | General | Set Baud Rate handshake Timeout
 `CAF0`              | **YES**     | CAN     | CAN Automatic Formatting Off
 `CAF1`              | **YES**     | CAN     | CAN Automatic Formatting On
 `CEA`               | **YES**     | CAN     | Turn off CAN Extended Addressing
 `CEA hh`            | **YES**     | CAN     | Use CAN Extended Address hh
 `CER hh`            | _NO_        | CAN     | Set CAN Extended Rx address to hh
 `CF hh hh hh hh`    | _NO_        | CAN     | Set the ID Filter to hhhhhhhh
 `CF hhh`            | _NO_        | CAN     | Set the ID Filter to hhh
 `CFC0`              | **YES**     | CAN     | CAN Flow Control Off
 `CFC1`              | **YES**     | CAN     | CAN Flow Control On
 `CM hh hh hh hh`    | _NO_        | CAN     | Set the ID Mask to hhhhhhhh
 `CM hhh`            | _NO_        | CAN     | Set the ID Mask to hhh
 `CP hh`             | _NO_        | CAN     | Set CAN Priority (only for 29 bit)
 `CRA`               | _NO_        | CAN     | Reset CAN Receive Address filters
 `CRA hhh`           | _NO_        | CAN     | Set CAN Receive Address to hhh
 `CRA hhhhhhhh`      | _NO_        | CAN     | Set CAN Receive Address to hhhhhhhh
 `CS`                | _NO_        | CAN     | Show the CAN Status
 `CSM0`              | **YES**     | CAN     | CAN Silent Mode Off
 `CSM1`              | **YES**     | CAN     | CAN Silent Mode On
 `CTM1`              | _NO_        | CAN     | Set Timer Multiplier to 1*
 `CTM5`              | _NO_        | CAN     | Set Timer Multiplier to 5
 `CV dddd`           | **YES**     | Voltage | Calibrate the Voltage to dd.dd volts
 `CV 0000`           | **YES**     | Voltage | Restore CV value to factory setting
 `D`                 | **YES**     | General | Set all to Defaults
 `D0`                | **YES**     | CAN     | Display of the DLC Off
 `D1`                | **YES**     | CAN     | Display of the DLC On
 `DM1`               | _NO_        | J1939   | (J1939) Monitor for DM1 messages
 `DP`                | **YES**     | OBD     | Describe the current Protocol
 `DPN`               | **YES**     | OBD     | Describe the Protocol by Number
 `E0`                | **YES**     | General | Echo Off
 `E1`                | **YES**     | General | Echo On
 `FC SD [1-5 bytes]` | _NO_        | CAN     | Flow Control Set Data to \[...\]
 `FC SH hh hh hh hh` | _NO_        | CAN     | Flow Control Set the Header to hhhhhhhh
 `FC SH hhh`         | _NO_        | CAN     | Flow Control Set the Header to hhh
 `FC SM h`           | _NO_        | CAN     | Flow Control Set the Mode to h
 `FE`                | _NO_        | General | Forget Events
 `FI`                | **YES**     | ISO     | Perform a Fast Initiation
 `H0`                | **YES**     | OBD     | Headers Off
 `H1`                | **YES**     | OBD     | Headers On
 `I`                 | **YES**     | General | Print the ID
 `IB 10`             | **YES**     | ISO     | Set the ISO Baud rate to 10400
 `IB 12`             | **YES**     | ISO     | Set the ISO Baud rate to 12500
 `IB 15`             | **YES**     | ISO     | Set the ISO Baud rate to 15625
 `IB 48`             | **YES**     | ISO     | Set the ISO Baud rate to 4800
 `IB 96`             | **YES**     | ISO     | Set the ISO Baud rate to 9600
 `IFR H`             | _NO_        | J1850   | IFR value from Header
 `IFR S`             | _NO_        | J1850   | IFR value from Source
 `IFR0`              | _NO_        | J1850   | IFRs Off, if not monitoring
 `IFR1`              | _NO_        | J1850   | IFRs Auto, if not monitoring
 `IFR2`              | _NO_        | J1850   | IFRs On, if not monitoring
 `IFR3`              | _NO_        | J1850   | IFRs Off, all the times
 `IFR4`              | _NO_        | J1850   | IFRs Auto, all the times
 `IFR5`              | _NO_        | J1850   | IFRs On, all the times
 `IGN`               | **YES**     | Other   | Read the IgnMon input level Other
 `IIA hh`            | _NO_        | ISO     | Set the ISO (slow) Init Address to hh
 `JE`                | _NO_        | J1939   | Use J1939 Elm data format
 `JHF0`              | **YES**     | J1939   | J1939 Header Formatting Off
 `JHF1`              | **YES**     | J1939   | J1939 Header Formatting On
 `JS`                | _NO_        | J1939   | Use J1939 SAE data format
 `JTM1`              | _NO_        | J1939   | Set the J1939 Timer Multiplier to 1x
 `JTM5`              | _NO_        | J1939   | Set the J1939 Timer Multiplier to 5x
 `KW`                | **YES**     | ISO     | Display the Key Words
 `KW0`               | **YES**     | ISO     | Key Word checking Off
 `KW1`               | **YES**     | ISO     | Key Word checking On
 `L0`                | **YES**     | General | Linefeeds Off
 `L1`                | **YES**     | General | Linefeeds On
 `LP`                | **YES**     | General | Go to Low Power mode
 `M0`                | **YES**     | General | Memory Off
 `M1`                | **YES**     | General | Memory On
 `MA`                | **YES**     | OBD     | Monitor All
 `MP hhhh`           | _NO_        | J1939   | (J1939) Monitor for PGN hhhh
 `MP hhhh n`         | _NO_        | J1939   | (J1939) Monitor for PGN hhhh, get n messages
 `MP hhhhhh`         | _NO_        | J1939   | (J1939) Monitor for PGN hhhhhh
 `MP hhhhhh n`       | _NO_        | J1939   | (J1939) Monitor for PGN hhhhhh, get n messages
 `MR hh`             | _NO_        | OBD     | Monitor for Receiver = hh
 `MT hh`             | _NO_        | OBD     | Monitor for Transmitter = hh
 `NL`                | **YES**     | OBD     | Normal Length (7 byte) messages
 `PB xx yy`          | _NO_        | CAN     | Set Protocol B options and baud rate
 `PC`                | **YES**     | OBD     | Protocol Close
 `PP FF OFF`         | **YES**     | PP      | All Prog Parameters Off
 `PP FF ON`          | **YES**     | PP      | All Prog Parameters On
 `PP xx OFF`         | **YES**     | PP      | Disable Prog Parameter xx
 `PP xx ON`          | **YES**     | PP      | Enable Prog Parameter xx
 `PP xx SV yy`       | **YES**     | PP      | For PP xx, Set the Value to yy
 `PPS`               | **YES**     | PP      | Print a PP Summary
 `R0`                | **YES**     | OBD     | Responses Off
 `R1`                | **YES**     | OBD     | Responses On
 `RA hh`             | _NO_        | OBD     | Set the Receive Address to hh
 `RD`                | **YES**     | General | Read the stored Data
 `RTR`               | _NO_        | CAN     | Send an RTR message
 `RV`                | **YES**     | Voltage | Read the Voltage
 `S0`                | **YES**     | OBD     | Printing of Spaces Off
 `S1`                | **YES**     | OBD     | Printing of Spaces On
 `SD hh`             | **YES**     | General | Store Data byte hh
 `SH ww xx yy zz`    | _NO_        | OBD     | Set Header
 `SH xx yy zz`       | _NO_        | OBD     | Set Header
 `SH yzz`            | _NO_        | OBD     | Set Header
 `SI`                | **YES**     | ISO     | Perform a Slow Initiation
 `SP Ah`             | **YES**     | OBD     | Set Protocol to Auto, h and save it
 `SP h`              | **YES**     | OBD     | Set Protocol to h and save it
 `SP 00`             | **YES**     | OBD     | Set Protocol to Auto and save it
 `SR hh`             | **YES**     | OBD     | Set the Receive address to hh
 `SS`                | _NO_        | OBD     | Set Standard Search order (J1978)
 `ST hh`             | **YES**     | OBD     | Set Timeout to hh x 4 msec
 `SW hh`             | **YES**     | ISO     | Set Wakeup interval to hh x 20 msec
 `SW 00`             | **YES**     | ISO     | Stop sending Wakeup messages
 `TA hh`             | _NO_        | OBD     | Set Tester Address to hh
 `TP Ah`             | **YES**     | OBD     | Try Protocol h with Auto search
 `TP h`              | **YES**     | OBD     | Try Protocol h
 `V0`                | **YES**     | CAN     | Use of Variable DLC Off
 `V1`                | **YES**     | CAN     | Use of Variable DLC On
 `WM [1-6 bytes]`    | _NO_        | ISO     | Set the Wakeup Message
 `WM xxyyzzaa`       | _NO_        | ISO     | Set the Wakeup Message to xxyyzzaa
 `WM xxyyzzaabb`     | _NO_        | ISO     | Set the Wakeup Message to xxyyzzaabb
 `WM xxyyzzaabbcc`   | _NO_        | ISO     | Set the Wakeup Message to xxyyzzaabbcc
 `WS`                | **YES**     | General | Warm Start
 `Z`                 | **YES**     | General | Reset all