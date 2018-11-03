/*
  Copyright (c) 2018 MacFJA

  Permission is hereby granted, free of charge,
  to any person obtaining a copy of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
  publish, distribute, sublicense, and/or sell copies of the Software,
  and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
  Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.TroubleCode;
import io.github.macfja.obd2.command.ClearDTCsCommand;
import io.github.macfja.obd2.command.DTCsCommand;
import io.github.macfja.obd2.command.livedata.EngineCoolantTemperature;
import io.github.macfja.obd2.command.livedata.EngineRPM;
import io.github.macfja.obd2.command.livedata.VehicleSpeed;
import io.github.macfja.obd2.commander.CommanderInterface;
import io.github.macfja.obd2.commander.ObdSimCommander;
import io.github.macfja.obd2.elm327.Commander;
import io.github.macfja.obd2.elm327.response.NoDataResponse;
import io.github.macfja.obd2.exception.ExceptionResponse;
import io.github.macfja.obd2.response.CalculatedResponse;
import io.github.macfja.obd2.response.MultipleDiagnosticTroubleCodeResponse;
import io.github.macfja.obd2.response.TemperatureResponse;

import javax.script.ScriptException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Really simple example on how to use the library
 *
 * @author MacFJA
 */
public class Application {
    private CommanderInterface commander;
    private JFrame mainWindow;
    private boolean commanderReady = false;

    private JButton clearCodes;
    private JList<TroubleCode> troubleCodeList = new JList<>();

    private JLabel speed = new JLabel("? (?)");
    private JLabel rpm = new JLabel("?");
    private JLabel coolant = new JLabel("? (?)");


    public static void main(String[] args) {
        new Application(args);
    }

    public Application(String[] args) {
        mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(true);

        mainWindow.getContentPane().setLayout(new BorderLayout());
        mainWindow.getContentPane().add(getListDataPanel(), BorderLayout.CENTER);
        mainWindow.getContentPane().add(getDTCPanel(), BorderLayout.EAST);

        List<String> params = Arrays.asList(args);
        initializeCommander(params);

        scheduleCommand();

        mainWindow.setVisible(true);
        mainWindow.pack();
    }

    private void initializeCommander(List<String> params) {
        if (params.size() == 2 && params.get(0).equals("obdsim")) {
            commander = new ObdSimCommander();
            try {
                commander.setCommunicationInterface(
                        new FileOutputStream("/dev/pts/"+params.get(1)),
                        new FileInputStream("/dev/pts/"+params.get(1))
                );
                commanderReady = true;
            } catch (FileNotFoundException e) {
                commanderReady = false;
            }
        } else if (params.size() == 2 && params.get(0).equals("serial")) {
            commander = new Commander();
            try {
                commander.setCommunicationInterface(
                        new FileOutputStream(params.get(1)),
                        new FileInputStream(params.get(1))
                );
                commanderReady = true;
            } catch (FileNotFoundException e) {
                commanderReady = false;
            }
        }
    }

    private void scheduleCommand() {
        (new Timer("Live Data")).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Response speedResponse = executeCommand(new VehicleSpeed());
                if (speedResponse instanceof CalculatedResponse) {
                    speed.setText(getAllUnitValue((CalculatedResponse) speedResponse));
                }
                Response rpmResponse = executeCommand(new EngineRPM());
                if (rpmResponse instanceof CalculatedResponse) {
                    rpm.setText(rpmResponse.getFormattedString());
                }
                Response coolantResponse = executeCommand(new EngineCoolantTemperature());
                if (coolantResponse instanceof TemperatureResponse) {
                    coolant.setText(getAllUnitValue((CalculatedResponse) coolantResponse));
                }
            }
        }, 0, 2000);

        (new Timer("DTCs")).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Response dtcResponse = executeCommand(new DTCsCommand());
                if (dtcResponse instanceof MultipleDiagnosticTroubleCodeResponse) {
                    troubleCodeList.setListData(((MultipleDiagnosticTroubleCodeResponse) dtcResponse).getTroubleCodes().toArray(new TroubleCode[0]));
                    clearCodes.setEnabled(!((MultipleDiagnosticTroubleCodeResponse) dtcResponse).getTroubleCodes().isEmpty());
                } else {
                    troubleCodeList.setListData(new TroubleCode[0]);
                    clearCodes.setEnabled(false);
                }
            }
        }, 0, 10000);
    }

    private String getAllUnitValue(CalculatedResponse response) {
        StringBuilder buidler = new StringBuilder(response.getFormattedString());
        buidler.append(" (");
        buidler.append(response.getUnit().toImperial(response.getCalculated().doubleValue()));
        buidler.append(response.getUnit().getImperialSymbol());
        buidler.append(")");

        return buidler.toString();
    }

    private JPanel getListDataPanel()
    {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Vehicle Speed"));
        panel.add(speed);
        panel.add(new JLabel("Engine RPM"));
        panel.add(rpm);
        panel.add(new JLabel("Coolant Temperature"));
        panel.add(coolant);

        return panel;
    }

    private JPanel getDTCPanel()
    {
        troubleCodeList.setCellRenderer(new ListCellRenderer<TroubleCode>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends TroubleCode> jList, TroubleCode troubleCode, int i, boolean b, boolean b1) {
                StringBuilder row = new StringBuilder(troubleCode.toString());

                row.append(" ");
                try {
                    row.append(troubleCode.getDescription());
                } catch (IOException e) {
                    // no-op
                }
                return new JLabel(row.toString());
            }
        });
        clearCodes = new JButton(new AbstractAction("Clear Codes") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                executeCommand(new ClearDTCsCommand());
            }
        });
        clearCodes.setEnabled(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(troubleCodeList, BorderLayout.CENTER);
        panel.add(clearCodes, BorderLayout.SOUTH);

        return panel;
    }

    private Response executeCommand(Command command)
    {
        if (!commanderReady) {
            return new NoDataResponse();
        }
        try {
            return commander.sendCommand(command);
        } catch (IOException | ScriptException | ExceptionResponse e) {
            JOptionPane.showMessageDialog(mainWindow, String.format(
                    "Unable to send the command '%s'.\nReason: %s",
                    command.getRequest(), e.getMessage()
            ));
            if (e instanceof ExceptionResponse) {
                return (Response) e;
            }
            return new ExceptionResponse(e.getMessage().getBytes());
        }
    }
}
