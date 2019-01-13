# Unit

Every Response have an information about the unit of the data.

The Unit class offer a way to what kind of data the response is, and how to display it.
The OBD2 standard use only **SI** unit (metric system), so the Unit class provide a way to convert any receive data from metric system to imperial system.

## List of all units

 Type of data     | Metric Unit | Imperial Unit | Conversion rate        | Java Enum
------------------|-------------|---------------|------------------------|-----------
 Any              | -           | -             | -                      | `Unit.NoUnit`, `Unit.Unknown`, `Unit.Multiple`
 Percentage       | **%**       | -             | -                      | `Unit.Percent`
 Temperature      | **°C**      | **°F**        | `1°F = 1.8°C + 32`     | `Unit.DegreeCelsius`
 Pressure         | **kPa**     | **psi**       | `1psi = 0.145038kPa`   | `Unit.KiloPascal`
 Rotational speed | **rpm**     | -             | -                      | `Unit.RoundPerMinute`
 Linear speed     | **km/h**    | **mph**       | `1mph = 0.621371km/h`  | `Unit.KilometrePerHour`
 Angle            | **°**       | -             | -                      | `Unit.Degree`
 Volumetric speed | **g/s**     | -             | -                      | `Unit.GramPerSecond`
 Voltage          | **V**       | -             | -                      | `Unit.Volt`
 Time             | **s**       | -             | -                      | `Unit.Second`
 Distance         | **km**      | **mi**        | `1mi = 0.621371km`     | `Unit.Kilometre`
 Pressure         | **Pa**      | **psi**       | `1psi = 0.000145Pa`    | `Unit.Pascal`
 Intensity        | **mA**      | -             | -                      | `Unit.Milliampere`
 Time             | **min**     | -             | -                      | `Unit.Minute`
 Volumetric speed | **L/h**     | **gal/h**     | `1gal/h = 0.264172L/h` | `Unit.LitrePerHour`
 Force            | **Nm**      | **lb-ft**     | `1lb-ft = 0.737562Nm`  | `Unit.NewtonMetre`

(Conversion are rounded, for the sake of readability)

## Example

```java
double vehicleSpeed = 120d;
Unit speedUnit = io.github.macfja.obd2.Unit.KilometrePerHour;

System.out.println(String.format("Metric: %d %s", vehicleSpeed, speedUnit.getSymbol());
System.out.println(String.format("Imperial: %.2f %s", speedUnit.toImperial(vehicleSpeed), speedUnit.getImperialSymbol());
```