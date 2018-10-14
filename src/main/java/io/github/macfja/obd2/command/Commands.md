# OBD commands

## Service `01` and Service `02`

 Code    | Implemented | Short description
---------|-------------|-------------------
 `01 00` | **YES**     | PIDs supported \[01 - 20\]
 `01 01` | **YES**     | Monitor status since DTCs cleared. (Includes malfunction indicator lamp (MIL) status and number of DTCs.)
 `01 02` | **YES**     | Freeze DTC
 `01 03` | **YES**     | Fuel system status
 `01 04` | **YES**     | Calculated engine load value
 `01 05` | **YES**     | Engine coolant temperature
 `01 06` | **YES**     | Short term fuel % trim—Bank 1
 `01 07` | **YES**     | Long term fuel % trim—Bank 1
 `01 08` | **YES**     | Short term fuel % trim—Bank 2
 `01 09` | **YES**     | Long term fuel % trim—Bank 2
 `01 0A` | **YES**     | Fuel pressure
 `01 0B` | **YES**     | Intake manifold absolute pressure
 `01 0C` | **YES**     | Engine RPM
 `01 0D` | **YES**     | Vehicle speed
 `01 0E` | **YES**     | Timing advance
 `01 0F` | **YES**     | Intake air temperature
 `01 10` | **YES**     | MAF air flow rate
 `01 11` | **YES**     | Throttle position
 `01 12` | **YES**     | Commanded secondary air status
 `01 13` | **YES**     | Oxygen sensors present
 `01 14` | **YES**     | Bank 1, Sensor 1: Oxygen sensor voltage, Short term fuel trim
 `01 15` | **YES**     | Bank 1, Sensor 2: Oxygen sensor voltage, Short term fuel trim
 `01 16` | **YES**     | Bank 1, Sensor 3: Oxygen sensor voltage, Short term fuel trim
 `01 17` | **YES**     | Bank 1, Sensor 4: Oxygen sensor voltage, Short term fuel trim
 `01 18` | **YES**     | Bank 2, Sensor 1: Oxygen sensor voltage, Short term fuel trim
 `01 19` | **YES**     | Bank 2, Sensor 2: Oxygen sensor voltage, Short term fuel trim
 `01 1A` | **YES**     | Bank 2, Sensor 3: Oxygen sensor voltage, Short term fuel trim
 `01 1B` | **YES**     | Bank 2, Sensor 4: Oxygen sensor voltage, Short term fuel trim
 `01 1C` | **YES**     | OBD standards this vehicle conforms to
 `01 1D` | **YES**     | Oxygen sensors present
 `01 1E` | **YES**     | Auxiliary input status
 `01 1F` | **YES**     | Run time since engine start
 `01 20` | **YES**     | PIDs supported \[21 - 40\]
 `01 21` | **YES**     | Distance traveled with malfunction indicator lamp (MIL) on
 `01 22` | **YES**     | Fuel Rail Pressure (relative to manifold vacuum)
 `01 23` | **YES**     | Fuel Rail Pressure (diesel, or gasoline direct inject)
 `01 24` | **YES**     | O2S1_WR_lambda(1): Equivalence Ratio, Voltage
 `01 25` | **YES**     | O2S2_WR_lambda(1): Equivalence Ratio, Voltage
 `01 26` | **YES**     | O2S3_WR_lambda(1): Equivalence Ratio, Voltage
 `01 27` | **YES**     | O2S4_WR_lambda(1): Equivalence Ratio, Voltage
 `01 28` | **YES**     | O2S5_WR_lambda(1): Equivalence Ratio, Voltage
 `01 29` | **YES**     | O2S6_WR_lambda(1): Equivalence Ratio, Voltage
 `01 2A` | **YES**     | O2S7_WR_lambda(1): Equivalence Ratio, Voltage
 `01 2B` | **YES**     | O2S8_WR_lambda(1): Equivalence Ratio, Voltage
 `01 2C` | **YES**     | Commanded EGR
 `01 2D` | **YES**     | EGR Error
 `01 2E` | **YES**     | Commanded evaporative purge
 `01 2F` | **YES**     | Fuel Level Input
 `01 30` | **YES**     | # of warm-ups since codes cleared
 `01 31` | **YES**     | Distance traveled since codes cleared
 `01 32` | _NO_        | Evap. System Vapor Pressure
 `01 33` | **YES**     | Barometric pressure
 `01 34` | **YES**     | O2S1_WR_lambda(1): Equivalence Ratio, Current
 `01 35` | **YES**     | O2S2_WR_lambda(1): Equivalence Ratio, Current
 `01 36` | **YES**     | O2S3_WR_lambda(1): Equivalence Ratio, Current
 `01 37` | **YES**     | O2S4_WR_lambda(1): Equivalence Ratio, Current
 `01 38` | **YES**     | O2S5_WR_lambda(1): Equivalence Ratio, Current
 `01 39` | **YES**     | O2S6_WR_lambda(1): Equivalence Ratio, Current
 `01 3A` | **YES**     | O2S7_WR_lambda(1): Equivalence Ratio, Current
 `01 3B` | **YES**     | O2S8_WR_lambda(1): Equivalence Ratio, Current
 `01 3C` | **YES**     | Catalyst Temperature Bank 1, Sensor 1 
 `01 3D` | **YES**     | Catalyst Temperature Bank 2, Sensor 1 
 `01 3E` | **YES**     | Catalyst Temperature Bank 1, Sensor 2 
 `01 3F` | **YES**     | Catalyst Temperature Bank 2, Sensor 2 
 `01 40` | **YES**     | PIDs supported \[41 - 60\]
 `01 41` | **YES**     | Monitor status this drive cycle
 `01 42` | **YES**     | Control module voltage
 `01 43` | **YES**     | Absolute load value
 `01 44` | **YES**     | Command equivalence ratio
 `01 45` | **YES**     | Relative throttle position 
 `01 46` | **YES**     | Ambient air temperature
 `01 47` | **YES**     | Absolute throttle position B 
 `01 48` | **YES**     | Absolute throttle position C 
 `01 49` | **YES**     | Accelerator pedal position D 
 `01 4A` | **YES**     | Accelerator pedal position E 
 `01 4B` | **YES**     | Accelerator pedal position F 
 `01 4C` | **YES**     | Commanded throttle actuator 
 `01 4D` | **YES**     | Time run with MIL on
 `01 4E` | **YES**     | Time since trouble codes cleared
 `01 4F` | **YES**     | Maximum value for equivalence ratio, oxygen sensor voltage, oxygen sensor current, and intake manifold absolute pressure
 `01 50` | **YES**     | Maximum value for air flow rate from mass air flow sensor
 `01 51` | **YES**     | Fuel Type
 `01 52` | **YES**     | Ethanol fuel % 
 `01 53` | **YES**     | Absolute Evap system Vapour Pressure
 `01 54` | **YES**     | Evap system vapor pressure
 `01 55` | **YES**     | Short term secondary oxygen sensor trim bank 1 and bank 3 
 `01 56` | **YES**     | Long term secondary oxygen sensor trim bank 1 and bank 3 
 `01 57` | **YES**     | Short term secondary oxygen sensor trim bank 2 and bank 4 
 `01 58` | **YES**     | Long term secondary oxygen sensor trim bank 2 and bank 4 
 `01 59` | **YES**     | Fuel rail pressure (absolute)
 `01 5A` | **YES**     | Relative accelerator pedal position
 `01 5B` | **YES**     | Hybrid battery pack remaining life
 `01 5C` | **YES**     | Engine oil temperature
 `01 5D` | **YES**     | Fuel injection timing
 `01 5E` | **YES**     | Engine fuel rate
 `01 5F` | _NO_        | Emission requirements to which vehicle is designed
 `01 60` | **YES**     | PIDs supported \[61 - 80\]
 `01 61` | **YES**     | Driver's demand engine - percent torque
 `01 62` | **YES**     | Actual engine - percent torque
 `01 63` | **YES**     | Engine reference torque
 `01 64` | _NO_        | Engine percent torque data
 `01 65` | _NO_        | Auxiliary input / output supported
 `01 66` | _NO_        | Mass air flow sensor
 `01 67` | _NO_        | Engine coolant temperature
 `01 68` | _NO_        | Intake air temperature sensor
 `01 69` | _NO_        | Commanded EGR and EGR Error
 `01 6A` | _NO_        | Commanded Diesel intake air flow control and relative intake air flow position
 `01 6B` | _NO_        | Exhaust gas recirculation temperature
 `01 6C` | _NO_        | Commanded throttle actuator control and relative throttle position
 `01 6D` | _NO_        | Fuel pressure control system
 `01 6E` | _NO_        | Injection pressure control system
 `01 6F` | _NO_        | Turbocharger compressor inlet pressure
 `01 70` | _NO_        | Boost pressure control
 `01 71` | _NO_        | Variable Geometry turbo (VGT) control
 `01 72` | _NO_        | Wastegate control
 `01 73` | _NO_        | Exhaust pressure
 `01 74` | _NO_        | Turbocharger RPM
 `01 75` | _NO_        | Turbocharger temperature
 `01 76` | _NO_        | Turbocharger temperature
 `01 77` | _NO_        | Charge air cooler temperature (CACT)
 `01 78` | _NO_        | Exhaust Gas temperature (EGT) Bank 1 Special PID. See below.
 `01 79` | _NO_        | Exhaust Gas temperature (EGT) Bank 2 Special PID. See below.
 `01 7A` | _NO_        | Diesel particulate filter (DPF)
 `01 7B` | _NO_        | Diesel particulate filter (DPF)
 `01 7C` | _NO_        | Diesel Particulate filter (DPF) temperature
 `01 7D` | _NO_        | NOx NTE control area status
 `01 7E` | _NO_        | PM NTE control area status
 `01 7F` | _NO_        | Engine run time
 `01 80` | **YES**     | PIDs supported \[81 - A0\]
 `01 81` | _NO_        | Engine run time for AECD
 `01 82` | _NO_        | Engine run time for AECD
 `01 83` | _NO_        | NOx sensor
 `01 84` | _NO_        | Manifold surface temperature
 `01 85` | _NO_        | NOx reagent system
 `01 86` | _NO_        | Particulate matter (PM) sensor
 `01 87` | _NO_        | Intake manifold absolute pressure
 `01 A0` | **YES**     | PIDs supported \[A1 - C0\]
 `01 C0` | **YES**     | PIDs supported \[C1 - E0\]
 `01 C3` | _NO_        | ?
 `01 C4` | _NO_        | ?
 `02 02` | _NO_        | Freeze frame trouble code BCD

## Service 03

**Implemented**

## Service 04

**Implemented**

## Service 05

 Code      | Implemented | Short description
-----------|-------------|-------------------
 `05 0100` | **YES**     | OBD Monitor IDs supported ($01 – $20)
 `05 0101` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 1, Rich to lean sensor threshold voltage
 `05 0102` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 2, Rich to lean sensor threshold voltage
 `05 0103` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 3, Rich to lean sensor threshold voltage
 `05 0104` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 4, Rich to lean sensor threshold voltage
 `05 0105` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 1, Rich to lean sensor threshold voltage
 `05 0106` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 2, Rich to lean sensor threshold voltage
 `05 0107` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 3, Rich to lean sensor threshold voltage
 `05 0108` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 4, Rich to lean sensor threshold voltage
 `05 0109` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 1, Rich to lean sensor threshold voltage
 `05 010A` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 2, Rich to lean sensor threshold voltage
 `05 010B` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 3, Rich to lean sensor threshold voltage
 `05 010C` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 4, Rich to lean sensor threshold voltage
 `05 010D` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 1, Rich to lean sensor threshold voltage
 `05 010E` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 2, Rich to lean sensor threshold voltage
 `05 010F` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 3, Rich to lean sensor threshold voltage
 `05 0110` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 4, Rich to lean sensor threshold voltage
 `05 0201` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 1, Lean to Rich sensor threshold voltage
 `05 0202` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 2, Lean to Rich sensor threshold voltage
 `05 0203` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 3, Lean to Rich sensor threshold voltage
 `05 0204` | **YES**     | O2 Sensor Monitor Bank 1 Sensor 4, Lean to Rich sensor threshold voltage
 `05 0205` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 1, Lean to Rich sensor threshold voltage
 `05 0206` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 2, Lean to Rich sensor threshold voltage
 `05 0207` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 3, Lean to Rich sensor threshold voltage
 `05 0208` | **YES**     | O2 Sensor Monitor Bank 2 Sensor 4, Lean to Rich sensor threshold voltage
 `05 0209` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 1, Lean to Rich sensor threshold voltage
 `05 020A` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 2, Lean to Rich sensor threshold voltage
 `05 020B` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 3, Lean to Rich sensor threshold voltage
 `05 020C` | **YES**     | O2 Sensor Monitor Bank 3 Sensor 4, Lean to Rich sensor threshold voltage
 `05 020D` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 1, Lean to Rich sensor threshold voltage
 `05 020E` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 2, Lean to Rich sensor threshold voltage
 `05 020F` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 3, Lean to Rich sensor threshold voltage
 `05 0210` | **YES**     | O2 Sensor Monitor Bank 4 Sensor 4, Lean to Rich sensor threshold voltage

## Service 06

_Not implemented_

## Service 07

_Not implemented_

## Service 08

_Not implemented_

## Service 09

 Code    | Implemented | Short description
---------|-------------|-------------------
 `09 00` | **YES**     | Service 9 supported PIDs (01 to 20)
 `09 01` | **YES**     | VIN Message Count in PID 02. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.
 `09 02` | **YES**     | Vehicle Identification Number (VIN)
 `09 03` | _NO_        | Calibration ID message count for PID 04. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.
 `09 04` | _NO_        | Calibration ID
 `09 05` | _NO_        | Calibration verification numbers (CVN) message count for PID 06. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.
 `09 06` | _NO_        | Calibration Verification Numbers (CVN) Several CVN can be outputed (4 bytes each) the number of CVN and CALID must match
 `09 07` | _NO_        | In-use performance tracking message count for PID 08 and 0B. Only for ISO 9141-2, ISO 14230-4 and SAE J1850.
 `09 08` | _NO_        | In-use performance tracking for spark ignition vehicles
 `09 09` | _NO_        | ECU name message count for PID 0A
 `09 0A` | **YES**     | ECU name
 `09 0B` | _NO_        | In-use performance tracking for compression ignition vehicles

## Service 0A

_Not implemented_