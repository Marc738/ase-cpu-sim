package de.dhbw;

import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.alu.ALU;
import de.dhbw.units.control.Command;
import de.dhbw.units.control.ControlUnit;
import de.dhbw.units.control.StorageManager;
import de.dhbw.units.control.decoder.ArgDecoder;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.units.control.decoder.command.AddCommandDecoder;
import de.dhbw.units.control.decoder.command.CommandDecoder;
import de.dhbw.units.control.decoder.command.GetCommandDecoder;
import de.dhbw.units.control.decoder.command.SetCommandDecoder;
import de.dhbw.units.register.Register;

public class Main {

    public static void main(String[] args) {
        ProcessingUnit[] processingUnits = new ProcessingUnit[]{
                new ALU(),
                new Register(8)
        };
        Decoder decoder = new Decoder(new ArgDecoder(), new CommandDecoder[]{
                new AddCommandDecoder(),
                new SetCommandDecoder(),
                new GetCommandDecoder()
        });
        ControlUnit controlUnit = new ControlUnit(processingUnits, decoder, new StorageManager());
        Command command = Command.fromString("set r1 #11110000");
        controlUnit.process(command);
        command = Command.fromString("get r1");
        controlUnit.process(command);
    }

}