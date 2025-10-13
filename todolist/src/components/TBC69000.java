public class TBC69000 {
    private static final int t = 0x8000;
    private static final int p = 0x400;
    private static final int o = 0x200;
    private static final int s = 0x100;
    private static final int r = 0x80;
    private static final int i = 0x40;
    private static final int c = 0x2;
    private static final int z = 0x1;

    public int[] reg;
    public Memory mem;

    public TBC69000() {
        this.reg = new int[16];
        this.mem = new Memory();
    }

    private void updateFlags(int op1,int op2,int sum) {
        int flags = reg[14] & 0b1111100011111100;
        int p = 0;
        int o = 0;
        int s = 0;
        int c = 0;
        int z = 0;
        if ((sum ^ (sum * 0x2) ^ (sum * 0x4) ^ (sum * 0x8) ^ (sum * 0x10) ^ (sum * 0x20) ^ (sum * 0x40) ^ (sum * 0x80) ^ (sum * 0x100) ^ (sum * 0x200) ^ (sum * 0x400) ^ (sum * 0x800) ^ (sum * 0x1000) ^ (sum * 0x2000) ^ (sum * 0x4000) ^ (sum * 0x8000)) > 0)
            p = 0x400;
        if (((op1 ^ sum) & (op2 ^ sum) & 0x8000) > 0)
            o = 0x200;
        if (sum / 0x8000 == 1)
            s = 0x100;
        if (sum / 0x10000 == 0)
            c = 0x2;
        if (sum % 0x10000 == 0)
            z = 0x1;
        flags += p + o + s + c + z;
        reg[14] = flags;
    }

    public void execute() {
        cycles++;
        ir = mem.get16(reg[15]);
        reg[15] += 2;
        int opcode = ir >> 8;
        switch (opcode) {
            case 0x02:
                reg[13] = (reg[13] - 2) & 0xffff;
                mem.write16(reg[13],reg[15]);
                reg[15] = mem.read16(ir & 0xff);
                reg[14] = reg[14] & (0xffff - i);
                break;
            case 0x03:
                reg[15] = mem.read16(reg[13]);
                reg[13] = (reg[13] + 2) & 0xffff;
                reg[14] = reg[14] | i;
                break;
            case 0x04:
                if (checkCond((ir & 0xf0) >> 4))
                    reg[15] = reg[ir % 0x0f];
                break;
            case 0x05:
                break;
            case 0x06:
                if (checkCond((ir & 0xf0) >> 4)) {
                    reg[13] = (reg[13] - 2) & 0xffff;
                    mem.write16(reg[13],reg[15]);
                    reg[15] = reg[ir & 0x0f];
                }
                break;
            case 0x07:
                if (checkCond((ir & 0xf0) >> 4)) {
                    reg[15] = mem.read16(reg[13]);
                    reg[13] = (reg[13] + 2) & 0xffff;
                }
                break;
            case 0x10:
                jr(0);
                break;
            case 0x11:
                jr(1);
                break;
            case 0x12:
                jr(2);
                break;
            case 0x13:
                jr(3);
                break;
            case 0x14:
                jr(4);
                break;
            case 0x15:
                jr(5);
                break;
            case 0x16:
                jr(6);
                break;
            case 0x17:
                jr(7);
                break;
            case 0x18:
                jr(8);
                break;
            case 0x19:
                jr(9);
                break;
            case 0x1a:
                jr(10);
                break;
            case 0x1b:
                jr(11);
                break;
            case 0x1c:
                jr(12);
                break;
            case 0x1d:
                jr(13);
                break;
            case 0x1e:
                jr(14);
                break;
            case 0x1f:
                jr(15);
                break;
            case 0x20:
                movl(0);
                break;
            case 0x21:
                movl(1);
                break;
            case 0x22:
                movl(2);
                break;
            case 0x23:
                movl(3);
                break;
            case 0x24:
                movl(4);
                break;
            case 0x25:
                movl(5);
                break;
            case 0x26:
                movl(6);
                break;
            case 0x27:
                movl(7);
                break;
            case 0x28:
                movl(8);
                break;
            case 0x29:
                movl(9);
                break;
            case 0x2a:
                movl(10);
                break;
            case 0x2b:
                movl(11);
                break;
            case 0x2c:
                movl(12);
                break;
            case 0x2d:
                movl(13);
                break;
            case 0x2e:
                movl(14);
                break;
            case 0x2f:
                movl(15);
                break;
            case 0x30:
                movh(0);
                break;
            case 0x31:
                movh(1);
                break;
            case 0x32:
                movh(2);
                break;
            case 0x33:
                movh(3);
                break;
            case 0x34:
                movh(4);
                break;
            case 0x35:
                movh(5);
                break;
            case 0x36:
                movh(6);
                break;
            case 0x37:
                movh(7);
                break;
            case 0x38:
                movh(8);
                break;
            case 0x39:
                movh(9);
                break;
            case 0x3a:
                movh(10);
                break;
            case 0x3b:
                movh(11);
                break;
            case 0x3c:
                movh(12);
                break;
            case 0x3d:
                movh(13);
                break;
            case 0x3e:
                movh(14);
                break;
            case 0x3f:
                movh(15);
                break;
            case 0x41:
                reg[(ir & 0xf0) >> 4] = mem.read8(reg[ir % 0x0f]);
                break;
            case 0x42:
                mem.write8(reg[ir & 0x0f],reg[(ir & 0xf0) >> 4]);
                break;
            case 0x44:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] + 1) & 0xffff;
                reg[(ir & 0xf0) >> 4] = mem.read8(reg[op2]);
                break;
            case 0x45:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] - 1) & 0xffff;
                reg[(ir & 0xf0) >> 4] = mem.read8(reg[op2]);
                break;
            case 0x46:
                int op2 = ir & 0x0f;
                reg[(ir & 0xf0) >> 4] = mem.read8(reg[op2]);
                reg[op2] = (reg[op2] + 1) & 0xffff;
                break;
            case 0x47:
                int op2 = ir & 0x0f;
                reg[(ir & 0xf0) >> 4] = mem.read8(reg[op2]);
                reg[op2] = (reg[op2] - 1) & 0xffff;
                break;
            case 0x48:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] + 1) & 0xffff;
                mem.write8(reg[op2],reg[(ir & 0xf0) >> 4]);
                break;
            case 0x49:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] - 1) & 0xffff;
                mem.write8(reg[op2],reg[(ir & 0xf0) >> 4]);
                break;
            case 0x4a:
                int op2 = ir & 0x0f;
                mem.write8(reg[op2],reg[(ir & 0xf0) >> 4]);
                reg[op2] = (reg[op2] + 1) & 0xffff;
                break;
            case 0x4b:
                int op2 = ir & 0x0f;
                mem.write8(reg[op2],reg[(ir & 0xf0) >> 4]);
                reg[op2] = (reg[op2] - 1) & 0xffff;
                break;
            case 0x51:
                reg[(ir & 0xf0) >> 4] = mem.read16(reg[ir % 0x0f]);
                break;
            case 0x52:
                mem.write16(reg[ir & 0x0f],reg[(ir & 0xf0) >> 4]);
                break;
            case 0x54:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] + 1) & 0xffff;
                reg[(ir & 0xf0) >> 4] = mem.read16(reg[op2]);
                break;
            case 0x55:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] - 1) & 0xffff;
                reg[(ir & 0xf0) >> 4] = mem.read16(reg[op2]);
                break;
            case 0x56:
                int op2 = ir & 0x0f;
                reg[(ir & 0xf0) >> 4] = mem.read16(reg[op2]);
                reg[op2] = (reg[op2] + 1) & 0xffff;
                break;
            case 0x57:
                int op2 = ir & 0x0f;
                reg[(ir & 0xf0) >> 4] = mem.read16(reg[op2]);
                reg[op2] = (reg[op2] - 1) & 0xffff;
                break;
            case 0x58:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] + 1) & 0xffff;
                mem.write16(reg[op2],reg[(ir & 0xf0) >> 4]);
                break;
            case 0x59:
                int op2 = ir & 0x0f;
                reg[op2] = (reg[op2] - 1) & 0xffff;
                mem.write16(reg[op2],reg[(ir & 0xf0) >> 4]);
                break;
            case 0x5a:
                int op2 = ir & 0x0f;
                mem.write16(reg[op2],reg[(ir & 0xf0) >> 4]);
                reg[op2] = (reg[op2] + 1) & 0xffff;
                break;
            case 0x5b:
                int op2 = ir & 0x0f;
                mem.write16(reg[op2],reg[(ir & 0xf0) >> 4]);
                reg[op2] = (reg[op2] - 1) & 0xffff;
                break;
            case 0x60:
                movbrr(0);
                break;
            case 0x61:
                movbrr(1);
                break;
            case 0x62:
                movbrr(2);
                break;
            case 0x63:
                movbrr(3);
                break;
            case 0x64:
                movbrr(4);
                break;
            case 0x65:
                movbrr(5);
                break;
            case 0x66:
                movbrr(6);
                break;
            case 0x67:
                movbrr(7);
                break;
            case 0x68:
                movbrr(8);
                break;
            case 0x69:
                movbrr(9);
                break;
            case 0x6a:
                movbrr(10);
                break;
            case 0x6b:
                movbrr(11);
                break;
            case 0x6c:
                movbrr(12);
                break;
            case 0x6d:
                movbrr(13);
                break;
            case 0x6e:
                movbrr(14);
                break;
            case 0x6f:
                movbrr(15);
                break;
            case 0x70:
                movrrb(0);
                break;
            case 0x71:
                movrrb(1);
                break;
            case 0x72:
                movrrb(2);
                break;
            case 0x73:
                movrrb(3);
                break;
            case 0x74:
                movrrb(4);
                break;
            case 0x75:
                movrrb(5);
                break;
            case 0x76:
                movrrb(6);
                break;
            case 0x77:
                movrrb(7);
                break;
            case 0x78:
                movrrb(8);
                break;
            case 0x79:
                movrrb(9);
                break;
            case 0x7a:
                movrrb(10);
                break;
            case 0x7b:
                movrrb(11);
                break;
            case 0x7c:
                movrrb(12);
                break;
            case 0x7d:
                movrrb(13);
                break;
            case 0x7e:
                movrrb(14);
                break;
            case 0x7f:
                movrrb(15);
                break;
            case 0x80:
                movwrr(0);
                break;
            case 0x81:
                movwrr(1);
                break;
            case 0x82:
                movwrr(2);
                break;
            case 0x83:
                movwrr(3);
                break;
            case 0x84:
                movwrr(4);
                break;
            case 0x85:
                movwrr(5);
                break;
            case 0x86:
                movwrr(6);
                break;
            case 0x87:
                movwrr(7);
                break;
            case 0x88:
                movwrr(8);
                break;
            case 0x89:
                movwrr(9);
                break;
            case 0x8a:
                movwrr(10);
                break;
            case 0x8b:
                movwrr(11);
                break;
            case 0x8c:
                movwrr(12);
                break;
            case 0x8d:
                movwrr(13);
                break;
            case 0x8e:
                movwrr(14);
                break;
            case 0x8f:
                movwrr(15);
                break;
            case 0x90:
                movrrw(0);
                break;
            case 0x91:
                movrrw(1);
                break;
            case 0x92:
                movrrw(2);
                break;
            case 0x93:
                movrrw(3);
                break;
            case 0x94:
                movrrw(4);
                break;
            case 0x95:
                movrrw(5);
                break;
            case 0x96:
                movrrw(6);
                break;
            case 0x97:
                movrrw(7);
                break;
            case 0x98:
                movrrw(8);
                break;
            case 0x99:
                movrrw(9);
                break;
            case 0x9a:
                movrrw(10);
                break;
            case 0x9b:
                movrrw(11);
                break;
            case 0x9c:
                movrrw(12);
                break;
            case 0x9d:
                movrrw(13);
                break;
            case 0x9e:
                movrrw(14);
                break;
            case 0x9f:
                movrrw(15);
                break;
            case 0xa0:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] + op2) & 0xffff;
                break;
            case 0xa1:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] + op2;
                updateFlags(reg[op1],op2,sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xa2:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] + (2 >> op2)) & 0xffff;
                break;
            case 0xa3:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] + (2 >> op2);
                updateFlags(reg[op1],2 >> op2,sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xa4:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] - op2) & 0xffff;
                break;
            case 0xa5:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] - op2;
                updateFlags(reg[op1],op2,sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xa6:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] - (2 >> op2)) & 0xffff;
                break;
            case 0xa7:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] - (2 >> op2);
                updateFlags(reg[op1],2 >> op2,sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xa8:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] + reg[op2]) & 0xffff;
                break;
            case 0xa9:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = (reg[op1] + reg[op2]);
                updateFlags(reg[op1],reg[op2],sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xaa:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) >> 1;
                reg[op1] = (reg[op1] + reg[op2] + cin) & 0xffff;
                break;
            case 0xab:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) >> 1;
                int sum = (reg[op1] + reg[op2] + cin);
                updateFlags(reg[op1],reg[op2],sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xac:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] - reg[op2]) & 0xffff;
                break;
            case 0xad:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = (reg[op1] - reg[op2]);
                updateFlags(reg[op1],reg[op2],sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xae:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) >> 1;
                reg[op1] = (reg[op1] - reg[op2] - cin) & 0xffff;
                break;
            case 0xaf:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) >> 1;
                int sum = (reg[op1] - reg[op2] - cin);
                updateFlags(reg[op1],reg[op2],sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xb0:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] * reg[op2]) & 0xffff;
                break;
            case 0xb1:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] * reg[op2];
                updateFlags(reg[op1],reg[op2],sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xb4:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] / reg[op2]) & 0xffff;
                break;
            case 0xb5:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] / reg[op2];
                updateFlags(reg[op1],reg[op2],sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xb8:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] % reg[op2]) & 0xffff;
                break;
            case 0xb9:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] % reg[op2];
                updateFlags(reg[op1],reg[op2],sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xbc:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                updateFlags(reg[op1],reg[op2],reg[op1] - reg[op2]);
                break;
            case 0xbe:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                updateFlags(reg[op1],reg[op2],reg[op1] & reg[op2]);
                break;
            case 0xc0:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = reg[op2];
                break;
            case 0xc1:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                updateFlags(reg[op2],reg[op2],reg[op2]);
                reg[op1] = reg[op2];
                break;
            case 0xc2:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = 0xffff - reg[op2];
                break;
            case 0xc3:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = 0xffff - reg[op2];
                updateFlags(reg[op2],reg[op2],sum);
                break;
            case 0xc4:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = op1 | op2;
                break;
            case 0xc5:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = op1 | op2;
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xc6:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = 0xffff - (op1 | op2);
                break;
            case 0xc7:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = 0xffff - (op1 | op2);
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xc8:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = op1 & op2;
                break;
            case 0xc9:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = op1 & op2;
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xca:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = 0xffff - (op1 & op2);
                break;
            case 0xcb:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = 0xffff - (op1 & op2);
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xcc:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = op1 ^ op2;
                break;
            case 0xcd:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = op1 ^ op2;
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xce:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = 0xffff - (op1 ^ op2);
                break;
            case 0xcf:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = 0xffff - (op1 ^ op2);
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xd0:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] << op2) & 0xffff;
                break;
            case 0xd1:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] << op2;
                updateFlags(op1,op2,sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xd2:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) >> 1;
                reg[op1] = (reg[op1] << op2 + cin) & 0xffff;
                break;
            case 0xd3:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) >> 1;
                int sum = reg[op1] << op2 + cin;
                updateFlags(op1,op2,sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xd4:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = reg[op1] >> op2;
                break;
            case 0xd5:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = reg[op1] >> op2;
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xd6:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) << 14;
                reg[op1] = reg[op1] >> op2 + cin;
                break;
            case 0xd7:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int cin = (reg[14] & c) << 14;
                int sum = reg[op1] >> op2 + cin;
                updateFlags(op1,op2,sum);
                reg[op1] = sum;
                break;
            case 0xd8:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = ((reg[op1] << 16) >> (op2 + 16)) & 0xffff;
                break;
            case 0xd9:
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                int sum = (reg[op1] << 16) >> (op2 + 16);
                updateFlags(op1,op2,sum);
                reg[op1] = sum & 0xffff;
                break;
            case 0xdc: // rol r, i4
                int op1 = (ir & 0xf0) >> 4;
                int op2 = ir & 0x0f;
                reg[op1] = (reg[op1] << op2) & 0xffff;
                break;
        }
    }
}