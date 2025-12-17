package aoc.intcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IntCodeComputer {

    private final long[] initialMemory;
    private long[] memory;
    private int instructionPointer = 0;
    private final Queue<Long> inputQueue = new LinkedList<>();
    private final List<Long> output = new ArrayList<>();

    public IntCodeComputer(String program) {
        this.initialMemory = Arrays.stream(program.split(","))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();
        this.memory = Arrays.copyOf(this.initialMemory, this.initialMemory.length);
    }

    public void reset() {
        this.memory = Arrays.copyOf(this.initialMemory, this.initialMemory.length);
        this.instructionPointer = 0;
        this.inputQueue.clear();
        this.output.clear();
    }

    public List<Long> run() {
        while (instructionPointer < memory.length) {
            int instruction = (int) memory[instructionPointer];
            int opcode = instruction % 100;

            switch (opcode) {
                case 1:
                    op_add(instruction);
                    break;
                case 2:
                    op_multiply(instruction);
                    break;
                case 3:
                    op_input();
                    break;
                case 4:
                    op_output(instruction);
                    break;
                case 5:
                    op_jumpIfTrue(instruction);
                    break;
                case 6:
                    op_jumpIfFalse(instruction);
                    break;
                case 7:
                    op_lessThan(instruction);
                    break;
                case 8:
                    op_equals(instruction);
                    break;
                case 99:
                    return output;
                default:
                    throw new IllegalStateException("Unknown opcode: " + opcode + " at position " + instructionPointer);
            }
        }
        throw new IllegalStateException("Program did not halt with opcode 99");
    }

    private void op_add(int instruction) {
        long param1 = getParameterValue(1, instruction);
        long param2 = getParameterValue(2, instruction);
        int outputPos = (int) memory[instructionPointer + 3];
        memory[outputPos] = param1 + param2;
        instructionPointer += 4;
    }

    private void op_multiply(int instruction) {
        long param1 = getParameterValue(1, instruction);
        long param2 = getParameterValue(2, instruction);
        int outputPos = (int) memory[instructionPointer + 3];
        memory[outputPos] = param1 * param2;
        instructionPointer += 4;
    }

    private void op_input() {
        if (inputQueue.isEmpty()) {
            throw new IllegalStateException("Input required, but queue is empty");
        }
        int outputPos = (int) memory[instructionPointer + 1];
        memory[outputPos] = inputQueue.poll();
        instructionPointer += 2;
    }

    private void op_output(int instruction) {
        long param1 = getParameterValue(1, instruction);
        output.add(param1);
        instructionPointer += 2;
    }

    private void op_jumpIfTrue(int instruction) {
        long param1 = getParameterValue(1, instruction);
        long param2 = getParameterValue(2, instruction);
        if (param1 != 0) {
            instructionPointer = (int) param2;
        } else {
            instructionPointer += 3;
        }
    }

    private void op_jumpIfFalse(int instruction) {
        long param1 = getParameterValue(1, instruction);
        long param2 = getParameterValue(2, instruction);
        if (param1 == 0) {
            instructionPointer = (int) param2;
        } else {
            instructionPointer += 3;
        }
    }

    private void op_lessThan(int instruction) {
        long param1 = getParameterValue(1, instruction);
        long param2 = getParameterValue(2, instruction);
        int outputPos = (int) memory[instructionPointer + 3];
        memory[outputPos] = (param1 < param2) ? 1 : 0;
        instructionPointer += 4;
    }

    private void op_equals(int instruction) {
        long param1 = getParameterValue(1, instruction);
        long param2 = getParameterValue(2, instruction);
        int outputPos = (int) memory[instructionPointer + 3];
        memory[outputPos] = (param1 == param2) ? 1 : 0;
        instructionPointer += 4;
    }


    private long getParameterValue(int paramIndex, int instruction) {
        int mode = (instruction / (int) Math.pow(10, paramIndex + 1)) % 10;
        long value = memory[instructionPointer + paramIndex];
        if (mode == 0) { // Position mode
            return memory[(int) value];
        } else { // Immediate mode
            return value;
        }
    }
    
    public Long runWithInput(Long... inputs) {
        addInput(inputs);
        List<Long> outputs = run();
        return outputs.get(outputs.size() - 1);
    }

    public void addInput(Long... inputs) {
        inputQueue.addAll(Arrays.asList(inputs));
    }

    public void setMemory(int position, long value) {
        memory[position] = value;
    }

    public long getMemory(int position) {
        return memory[position];
    }
}
