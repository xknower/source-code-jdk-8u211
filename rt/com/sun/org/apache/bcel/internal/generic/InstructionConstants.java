/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface InstructionConstants
/*     */ {
/*  82 */   public static final Instruction NOP = new NOP();
/*  83 */   public static final Instruction ACONST_NULL = new ACONST_NULL();
/*  84 */   public static final Instruction ICONST_M1 = new ICONST(-1);
/*  85 */   public static final Instruction ICONST_0 = new ICONST(0);
/*  86 */   public static final Instruction ICONST_1 = new ICONST(1);
/*  87 */   public static final Instruction ICONST_2 = new ICONST(2);
/*  88 */   public static final Instruction ICONST_3 = new ICONST(3);
/*  89 */   public static final Instruction ICONST_4 = new ICONST(4);
/*  90 */   public static final Instruction ICONST_5 = new ICONST(5);
/*  91 */   public static final Instruction LCONST_0 = new LCONST(0L);
/*  92 */   public static final Instruction LCONST_1 = new LCONST(1L);
/*  93 */   public static final Instruction FCONST_0 = new FCONST(0.0F);
/*  94 */   public static final Instruction FCONST_1 = new FCONST(1.0F);
/*  95 */   public static final Instruction FCONST_2 = new FCONST(2.0F);
/*  96 */   public static final Instruction DCONST_0 = new DCONST(0.0D);
/*  97 */   public static final Instruction DCONST_1 = new DCONST(1.0D);
/*  98 */   public static final ArrayInstruction IALOAD = new IALOAD();
/*  99 */   public static final ArrayInstruction LALOAD = new LALOAD();
/* 100 */   public static final ArrayInstruction FALOAD = new FALOAD();
/* 101 */   public static final ArrayInstruction DALOAD = new DALOAD();
/* 102 */   public static final ArrayInstruction AALOAD = new AALOAD();
/* 103 */   public static final ArrayInstruction BALOAD = new BALOAD();
/* 104 */   public static final ArrayInstruction CALOAD = new CALOAD();
/* 105 */   public static final ArrayInstruction SALOAD = new SALOAD();
/* 106 */   public static final ArrayInstruction IASTORE = new IASTORE();
/* 107 */   public static final ArrayInstruction LASTORE = new LASTORE();
/* 108 */   public static final ArrayInstruction FASTORE = new FASTORE();
/* 109 */   public static final ArrayInstruction DASTORE = new DASTORE();
/* 110 */   public static final ArrayInstruction AASTORE = new AASTORE();
/* 111 */   public static final ArrayInstruction BASTORE = new BASTORE();
/* 112 */   public static final ArrayInstruction CASTORE = new CASTORE();
/* 113 */   public static final ArrayInstruction SASTORE = new SASTORE();
/* 114 */   public static final StackInstruction POP = new POP();
/* 115 */   public static final StackInstruction POP2 = new POP2();
/* 116 */   public static final StackInstruction DUP = new DUP();
/* 117 */   public static final StackInstruction DUP_X1 = new DUP_X1();
/* 118 */   public static final StackInstruction DUP_X2 = new DUP_X2();
/* 119 */   public static final StackInstruction DUP2 = new DUP2();
/* 120 */   public static final StackInstruction DUP2_X1 = new DUP2_X1();
/* 121 */   public static final StackInstruction DUP2_X2 = new DUP2_X2();
/* 122 */   public static final StackInstruction SWAP = new SWAP();
/* 123 */   public static final ArithmeticInstruction IADD = new IADD();
/* 124 */   public static final ArithmeticInstruction LADD = new LADD();
/* 125 */   public static final ArithmeticInstruction FADD = new FADD();
/* 126 */   public static final ArithmeticInstruction DADD = new DADD();
/* 127 */   public static final ArithmeticInstruction ISUB = new ISUB();
/* 128 */   public static final ArithmeticInstruction LSUB = new LSUB();
/* 129 */   public static final ArithmeticInstruction FSUB = new FSUB();
/* 130 */   public static final ArithmeticInstruction DSUB = new DSUB();
/* 131 */   public static final ArithmeticInstruction IMUL = new IMUL();
/* 132 */   public static final ArithmeticInstruction LMUL = new LMUL();
/* 133 */   public static final ArithmeticInstruction FMUL = new FMUL();
/* 134 */   public static final ArithmeticInstruction DMUL = new DMUL();
/* 135 */   public static final ArithmeticInstruction IDIV = new IDIV();
/* 136 */   public static final ArithmeticInstruction LDIV = new LDIV();
/* 137 */   public static final ArithmeticInstruction FDIV = new FDIV();
/* 138 */   public static final ArithmeticInstruction DDIV = new DDIV();
/* 139 */   public static final ArithmeticInstruction IREM = new IREM();
/* 140 */   public static final ArithmeticInstruction LREM = new LREM();
/* 141 */   public static final ArithmeticInstruction FREM = new FREM();
/* 142 */   public static final ArithmeticInstruction DREM = new DREM();
/* 143 */   public static final ArithmeticInstruction INEG = new INEG();
/* 144 */   public static final ArithmeticInstruction LNEG = new LNEG();
/* 145 */   public static final ArithmeticInstruction FNEG = new FNEG();
/* 146 */   public static final ArithmeticInstruction DNEG = new DNEG();
/* 147 */   public static final ArithmeticInstruction ISHL = new ISHL();
/* 148 */   public static final ArithmeticInstruction LSHL = new LSHL();
/* 149 */   public static final ArithmeticInstruction ISHR = new ISHR();
/* 150 */   public static final ArithmeticInstruction LSHR = new LSHR();
/* 151 */   public static final ArithmeticInstruction IUSHR = new IUSHR();
/* 152 */   public static final ArithmeticInstruction LUSHR = new LUSHR();
/* 153 */   public static final ArithmeticInstruction IAND = new IAND();
/* 154 */   public static final ArithmeticInstruction LAND = new LAND();
/* 155 */   public static final ArithmeticInstruction IOR = new IOR();
/* 156 */   public static final ArithmeticInstruction LOR = new LOR();
/* 157 */   public static final ArithmeticInstruction IXOR = new IXOR();
/* 158 */   public static final ArithmeticInstruction LXOR = new LXOR();
/* 159 */   public static final ConversionInstruction I2L = new I2L();
/* 160 */   public static final ConversionInstruction I2F = new I2F();
/* 161 */   public static final ConversionInstruction I2D = new I2D();
/* 162 */   public static final ConversionInstruction L2I = new L2I();
/* 163 */   public static final ConversionInstruction L2F = new L2F();
/* 164 */   public static final ConversionInstruction L2D = new L2D();
/* 165 */   public static final ConversionInstruction F2I = new F2I();
/* 166 */   public static final ConversionInstruction F2L = new F2L();
/* 167 */   public static final ConversionInstruction F2D = new F2D();
/* 168 */   public static final ConversionInstruction D2I = new D2I();
/* 169 */   public static final ConversionInstruction D2L = new D2L();
/* 170 */   public static final ConversionInstruction D2F = new D2F();
/* 171 */   public static final ConversionInstruction I2B = new I2B();
/* 172 */   public static final ConversionInstruction I2C = new I2C();
/* 173 */   public static final ConversionInstruction I2S = new I2S();
/* 174 */   public static final Instruction LCMP = new LCMP();
/* 175 */   public static final Instruction FCMPL = new FCMPL();
/* 176 */   public static final Instruction FCMPG = new FCMPG();
/* 177 */   public static final Instruction DCMPL = new DCMPL();
/* 178 */   public static final Instruction DCMPG = new DCMPG();
/* 179 */   public static final ReturnInstruction IRETURN = new IRETURN();
/* 180 */   public static final ReturnInstruction LRETURN = new LRETURN();
/* 181 */   public static final ReturnInstruction FRETURN = new FRETURN();
/* 182 */   public static final ReturnInstruction DRETURN = new DRETURN();
/* 183 */   public static final ReturnInstruction ARETURN = new ARETURN();
/* 184 */   public static final ReturnInstruction RETURN = new RETURN();
/* 185 */   public static final Instruction ARRAYLENGTH = new ARRAYLENGTH();
/* 186 */   public static final Instruction ATHROW = new ATHROW();
/* 187 */   public static final Instruction MONITORENTER = new MONITORENTER();
/* 188 */   public static final Instruction MONITOREXIT = new MONITOREXIT();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static final LocalVariableInstruction THIS = new ALOAD(0);
/* 194 */   public static final LocalVariableInstruction ALOAD_0 = THIS;
/* 195 */   public static final LocalVariableInstruction ALOAD_1 = new ALOAD(1);
/* 196 */   public static final LocalVariableInstruction ALOAD_2 = new ALOAD(2);
/* 197 */   public static final LocalVariableInstruction ILOAD_0 = new ILOAD(0);
/* 198 */   public static final LocalVariableInstruction ILOAD_1 = new ILOAD(1);
/* 199 */   public static final LocalVariableInstruction ILOAD_2 = new ILOAD(2);
/* 200 */   public static final LocalVariableInstruction ASTORE_0 = new ASTORE(0);
/* 201 */   public static final LocalVariableInstruction ASTORE_1 = new ASTORE(1);
/* 202 */   public static final LocalVariableInstruction ASTORE_2 = new ASTORE(2);
/* 203 */   public static final LocalVariableInstruction ISTORE_0 = new ISTORE(0);
/* 204 */   public static final LocalVariableInstruction ISTORE_1 = new ISTORE(1);
/* 205 */   public static final LocalVariableInstruction ISTORE_2 = new ISTORE(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 211 */   public static final Instruction[] INSTRUCTIONS = new Instruction[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public static final Clinit bla = new Clinit();
/*     */   
/*     */   public static class Clinit {
/*     */     Clinit() {
/* 220 */       InstructionConstants.INSTRUCTIONS[0] = InstructionConstants.NOP;
/* 221 */       InstructionConstants.INSTRUCTIONS[1] = InstructionConstants.ACONST_NULL;
/* 222 */       InstructionConstants.INSTRUCTIONS[2] = InstructionConstants.ICONST_M1;
/* 223 */       InstructionConstants.INSTRUCTIONS[3] = InstructionConstants.ICONST_0;
/* 224 */       InstructionConstants.INSTRUCTIONS[4] = InstructionConstants.ICONST_1;
/* 225 */       InstructionConstants.INSTRUCTIONS[5] = InstructionConstants.ICONST_2;
/* 226 */       InstructionConstants.INSTRUCTIONS[6] = InstructionConstants.ICONST_3;
/* 227 */       InstructionConstants.INSTRUCTIONS[7] = InstructionConstants.ICONST_4;
/* 228 */       InstructionConstants.INSTRUCTIONS[8] = InstructionConstants.ICONST_5;
/* 229 */       InstructionConstants.INSTRUCTIONS[9] = InstructionConstants.LCONST_0;
/* 230 */       InstructionConstants.INSTRUCTIONS[10] = InstructionConstants.LCONST_1;
/* 231 */       InstructionConstants.INSTRUCTIONS[11] = InstructionConstants.FCONST_0;
/* 232 */       InstructionConstants.INSTRUCTIONS[12] = InstructionConstants.FCONST_1;
/* 233 */       InstructionConstants.INSTRUCTIONS[13] = InstructionConstants.FCONST_2;
/* 234 */       InstructionConstants.INSTRUCTIONS[14] = InstructionConstants.DCONST_0;
/* 235 */       InstructionConstants.INSTRUCTIONS[15] = InstructionConstants.DCONST_1;
/* 236 */       InstructionConstants.INSTRUCTIONS[46] = InstructionConstants.IALOAD;
/* 237 */       InstructionConstants.INSTRUCTIONS[47] = InstructionConstants.LALOAD;
/* 238 */       InstructionConstants.INSTRUCTIONS[48] = InstructionConstants.FALOAD;
/* 239 */       InstructionConstants.INSTRUCTIONS[49] = InstructionConstants.DALOAD;
/* 240 */       InstructionConstants.INSTRUCTIONS[50] = InstructionConstants.AALOAD;
/* 241 */       InstructionConstants.INSTRUCTIONS[51] = InstructionConstants.BALOAD;
/* 242 */       InstructionConstants.INSTRUCTIONS[52] = InstructionConstants.CALOAD;
/* 243 */       InstructionConstants.INSTRUCTIONS[53] = InstructionConstants.SALOAD;
/* 244 */       InstructionConstants.INSTRUCTIONS[79] = InstructionConstants.IASTORE;
/* 245 */       InstructionConstants.INSTRUCTIONS[80] = InstructionConstants.LASTORE;
/* 246 */       InstructionConstants.INSTRUCTIONS[81] = InstructionConstants.FASTORE;
/* 247 */       InstructionConstants.INSTRUCTIONS[82] = InstructionConstants.DASTORE;
/* 248 */       InstructionConstants.INSTRUCTIONS[83] = InstructionConstants.AASTORE;
/* 249 */       InstructionConstants.INSTRUCTIONS[84] = InstructionConstants.BASTORE;
/* 250 */       InstructionConstants.INSTRUCTIONS[85] = InstructionConstants.CASTORE;
/* 251 */       InstructionConstants.INSTRUCTIONS[86] = InstructionConstants.SASTORE;
/* 252 */       InstructionConstants.INSTRUCTIONS[87] = InstructionConstants.POP;
/* 253 */       InstructionConstants.INSTRUCTIONS[88] = InstructionConstants.POP2;
/* 254 */       InstructionConstants.INSTRUCTIONS[89] = InstructionConstants.DUP;
/* 255 */       InstructionConstants.INSTRUCTIONS[90] = InstructionConstants.DUP_X1;
/* 256 */       InstructionConstants.INSTRUCTIONS[91] = InstructionConstants.DUP_X2;
/* 257 */       InstructionConstants.INSTRUCTIONS[92] = InstructionConstants.DUP2;
/* 258 */       InstructionConstants.INSTRUCTIONS[93] = InstructionConstants.DUP2_X1;
/* 259 */       InstructionConstants.INSTRUCTIONS[94] = InstructionConstants.DUP2_X2;
/* 260 */       InstructionConstants.INSTRUCTIONS[95] = InstructionConstants.SWAP;
/* 261 */       InstructionConstants.INSTRUCTIONS[96] = InstructionConstants.IADD;
/* 262 */       InstructionConstants.INSTRUCTIONS[97] = InstructionConstants.LADD;
/* 263 */       InstructionConstants.INSTRUCTIONS[98] = InstructionConstants.FADD;
/* 264 */       InstructionConstants.INSTRUCTIONS[99] = InstructionConstants.DADD;
/* 265 */       InstructionConstants.INSTRUCTIONS[100] = InstructionConstants.ISUB;
/* 266 */       InstructionConstants.INSTRUCTIONS[101] = InstructionConstants.LSUB;
/* 267 */       InstructionConstants.INSTRUCTIONS[102] = InstructionConstants.FSUB;
/* 268 */       InstructionConstants.INSTRUCTIONS[103] = InstructionConstants.DSUB;
/* 269 */       InstructionConstants.INSTRUCTIONS[104] = InstructionConstants.IMUL;
/* 270 */       InstructionConstants.INSTRUCTIONS[105] = InstructionConstants.LMUL;
/* 271 */       InstructionConstants.INSTRUCTIONS[106] = InstructionConstants.FMUL;
/* 272 */       InstructionConstants.INSTRUCTIONS[107] = InstructionConstants.DMUL;
/* 273 */       InstructionConstants.INSTRUCTIONS[108] = InstructionConstants.IDIV;
/* 274 */       InstructionConstants.INSTRUCTIONS[109] = InstructionConstants.LDIV;
/* 275 */       InstructionConstants.INSTRUCTIONS[110] = InstructionConstants.FDIV;
/* 276 */       InstructionConstants.INSTRUCTIONS[111] = InstructionConstants.DDIV;
/* 277 */       InstructionConstants.INSTRUCTIONS[112] = InstructionConstants.IREM;
/* 278 */       InstructionConstants.INSTRUCTIONS[113] = InstructionConstants.LREM;
/* 279 */       InstructionConstants.INSTRUCTIONS[114] = InstructionConstants.FREM;
/* 280 */       InstructionConstants.INSTRUCTIONS[115] = InstructionConstants.DREM;
/* 281 */       InstructionConstants.INSTRUCTIONS[116] = InstructionConstants.INEG;
/* 282 */       InstructionConstants.INSTRUCTIONS[117] = InstructionConstants.LNEG;
/* 283 */       InstructionConstants.INSTRUCTIONS[118] = InstructionConstants.FNEG;
/* 284 */       InstructionConstants.INSTRUCTIONS[119] = InstructionConstants.DNEG;
/* 285 */       InstructionConstants.INSTRUCTIONS[120] = InstructionConstants.ISHL;
/* 286 */       InstructionConstants.INSTRUCTIONS[121] = InstructionConstants.LSHL;
/* 287 */       InstructionConstants.INSTRUCTIONS[122] = InstructionConstants.ISHR;
/* 288 */       InstructionConstants.INSTRUCTIONS[123] = InstructionConstants.LSHR;
/* 289 */       InstructionConstants.INSTRUCTIONS[124] = InstructionConstants.IUSHR;
/* 290 */       InstructionConstants.INSTRUCTIONS[125] = InstructionConstants.LUSHR;
/* 291 */       InstructionConstants.INSTRUCTIONS[126] = InstructionConstants.IAND;
/* 292 */       InstructionConstants.INSTRUCTIONS[127] = InstructionConstants.LAND;
/* 293 */       InstructionConstants.INSTRUCTIONS[128] = InstructionConstants.IOR;
/* 294 */       InstructionConstants.INSTRUCTIONS[129] = InstructionConstants.LOR;
/* 295 */       InstructionConstants.INSTRUCTIONS[130] = InstructionConstants.IXOR;
/* 296 */       InstructionConstants.INSTRUCTIONS[131] = InstructionConstants.LXOR;
/* 297 */       InstructionConstants.INSTRUCTIONS[133] = InstructionConstants.I2L;
/* 298 */       InstructionConstants.INSTRUCTIONS[134] = InstructionConstants.I2F;
/* 299 */       InstructionConstants.INSTRUCTIONS[135] = InstructionConstants.I2D;
/* 300 */       InstructionConstants.INSTRUCTIONS[136] = InstructionConstants.L2I;
/* 301 */       InstructionConstants.INSTRUCTIONS[137] = InstructionConstants.L2F;
/* 302 */       InstructionConstants.INSTRUCTIONS[138] = InstructionConstants.L2D;
/* 303 */       InstructionConstants.INSTRUCTIONS[139] = InstructionConstants.F2I;
/* 304 */       InstructionConstants.INSTRUCTIONS[140] = InstructionConstants.F2L;
/* 305 */       InstructionConstants.INSTRUCTIONS[141] = InstructionConstants.F2D;
/* 306 */       InstructionConstants.INSTRUCTIONS[142] = InstructionConstants.D2I;
/* 307 */       InstructionConstants.INSTRUCTIONS[143] = InstructionConstants.D2L;
/* 308 */       InstructionConstants.INSTRUCTIONS[144] = InstructionConstants.D2F;
/* 309 */       InstructionConstants.INSTRUCTIONS[145] = InstructionConstants.I2B;
/* 310 */       InstructionConstants.INSTRUCTIONS[146] = InstructionConstants.I2C;
/* 311 */       InstructionConstants.INSTRUCTIONS[147] = InstructionConstants.I2S;
/* 312 */       InstructionConstants.INSTRUCTIONS[148] = InstructionConstants.LCMP;
/* 313 */       InstructionConstants.INSTRUCTIONS[149] = InstructionConstants.FCMPL;
/* 314 */       InstructionConstants.INSTRUCTIONS[150] = InstructionConstants.FCMPG;
/* 315 */       InstructionConstants.INSTRUCTIONS[151] = InstructionConstants.DCMPL;
/* 316 */       InstructionConstants.INSTRUCTIONS[152] = InstructionConstants.DCMPG;
/* 317 */       InstructionConstants.INSTRUCTIONS[172] = InstructionConstants.IRETURN;
/* 318 */       InstructionConstants.INSTRUCTIONS[173] = InstructionConstants.LRETURN;
/* 319 */       InstructionConstants.INSTRUCTIONS[174] = InstructionConstants.FRETURN;
/* 320 */       InstructionConstants.INSTRUCTIONS[175] = InstructionConstants.DRETURN;
/* 321 */       InstructionConstants.INSTRUCTIONS[176] = InstructionConstants.ARETURN;
/* 322 */       InstructionConstants.INSTRUCTIONS[177] = InstructionConstants.RETURN;
/* 323 */       InstructionConstants.INSTRUCTIONS[190] = InstructionConstants.ARRAYLENGTH;
/* 324 */       InstructionConstants.INSTRUCTIONS[191] = InstructionConstants.ATHROW;
/* 325 */       InstructionConstants.INSTRUCTIONS[194] = InstructionConstants.MONITORENTER;
/* 326 */       InstructionConstants.INSTRUCTIONS[195] = InstructionConstants.MONITOREXIT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\bcel\internal\generic\InstructionConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */