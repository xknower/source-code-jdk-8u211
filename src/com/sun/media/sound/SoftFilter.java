/*     */ package com.sun.media.sound;
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
/*     */ public final class SoftFilter
/*     */ {
/*     */   public static final int FILTERTYPE_LP6 = 0;
/*     */   public static final int FILTERTYPE_LP12 = 1;
/*     */   public static final int FILTERTYPE_HP12 = 17;
/*     */   public static final int FILTERTYPE_BP12 = 33;
/*     */   public static final int FILTERTYPE_NP12 = 49;
/*     */   public static final int FILTERTYPE_LP24 = 3;
/*     */   public static final int FILTERTYPE_HP24 = 19;
/*  57 */   private int filtertype = 0;
/*     */   private final float samplerate;
/*     */   private float x1;
/*     */   private float x2;
/*     */   private float y1;
/*     */   private float y2;
/*     */   private float xx1;
/*     */   private float xx2;
/*     */   private float yy1;
/*     */   private float yy2;
/*     */   private float a0;
/*     */   private float a1;
/*     */   private float a2;
/*     */   private float b1;
/*     */   private float b2;
/*     */   private float q;
/*  73 */   private float gain = 1.0F;
/*  74 */   private float wet = 0.0F;
/*  75 */   private float last_wet = 0.0F;
/*     */   private float last_a0;
/*     */   private float last_a1;
/*     */   private float last_a2;
/*     */   private float last_b1;
/*     */   private float last_b2;
/*     */   private float last_q;
/*     */   private float last_gain;
/*     */   private boolean last_set = false;
/*  84 */   private double cutoff = 44100.0D;
/*  85 */   private double resonancedB = 0.0D;
/*     */   private boolean dirty = true;
/*     */   
/*     */   public SoftFilter(float paramFloat) {
/*  89 */     this.samplerate = paramFloat;
/*  90 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public void setFrequency(double paramDouble) {
/*  94 */     if (this.cutoff == paramDouble)
/*     */       return; 
/*  96 */     this.cutoff = paramDouble;
/*  97 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public void setResonance(double paramDouble) {
/* 101 */     if (this.resonancedB == paramDouble)
/*     */       return; 
/* 103 */     this.resonancedB = paramDouble;
/* 104 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 108 */     this.dirty = true;
/* 109 */     this.last_set = false;
/* 110 */     this.x1 = 0.0F;
/* 111 */     this.x2 = 0.0F;
/* 112 */     this.y1 = 0.0F;
/* 113 */     this.y2 = 0.0F;
/* 114 */     this.xx1 = 0.0F;
/* 115 */     this.xx2 = 0.0F;
/* 116 */     this.yy1 = 0.0F;
/* 117 */     this.yy2 = 0.0F;
/* 118 */     this.wet = 0.0F;
/* 119 */     this.gain = 1.0F;
/* 120 */     this.a0 = 0.0F;
/* 121 */     this.a1 = 0.0F;
/* 122 */     this.a2 = 0.0F;
/* 123 */     this.b1 = 0.0F;
/* 124 */     this.b2 = 0.0F;
/*     */   }
/*     */   
/*     */   public void setFilterType(int paramInt) {
/* 128 */     this.filtertype = paramInt;
/*     */   }
/*     */   
/*     */   public void processAudio(SoftAudioBuffer paramSoftAudioBuffer) {
/* 132 */     if (this.filtertype == 0)
/* 133 */       filter1(paramSoftAudioBuffer); 
/* 134 */     if (this.filtertype == 1)
/* 135 */       filter2(paramSoftAudioBuffer); 
/* 136 */     if (this.filtertype == 17)
/* 137 */       filter2(paramSoftAudioBuffer); 
/* 138 */     if (this.filtertype == 33)
/* 139 */       filter2(paramSoftAudioBuffer); 
/* 140 */     if (this.filtertype == 49)
/* 141 */       filter2(paramSoftAudioBuffer); 
/* 142 */     if (this.filtertype == 3)
/* 143 */       filter4(paramSoftAudioBuffer); 
/* 144 */     if (this.filtertype == 19) {
/* 145 */       filter4(paramSoftAudioBuffer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void filter4(SoftAudioBuffer paramSoftAudioBuffer) {
/* 150 */     float[] arrayOfFloat = paramSoftAudioBuffer.array();
/*     */     
/* 152 */     if (this.dirty) {
/* 153 */       filter2calc();
/* 154 */       this.dirty = false;
/*     */     } 
/* 156 */     if (!this.last_set) {
/* 157 */       this.last_a0 = this.a0;
/* 158 */       this.last_a1 = this.a1;
/* 159 */       this.last_a2 = this.a2;
/* 160 */       this.last_b1 = this.b1;
/* 161 */       this.last_b2 = this.b2;
/* 162 */       this.last_gain = this.gain;
/* 163 */       this.last_wet = this.wet;
/* 164 */       this.last_set = true;
/*     */     } 
/*     */     
/* 167 */     if (this.wet > 0.0F || this.last_wet > 0.0F) {
/*     */       
/* 169 */       int i = arrayOfFloat.length;
/* 170 */       float f1 = this.last_a0;
/* 171 */       float f2 = this.last_a1;
/* 172 */       float f3 = this.last_a2;
/* 173 */       float f4 = this.last_b1;
/* 174 */       float f5 = this.last_b2;
/* 175 */       float f6 = this.last_gain;
/* 176 */       float f7 = this.last_wet;
/* 177 */       float f8 = (this.a0 - this.last_a0) / i;
/* 178 */       float f9 = (this.a1 - this.last_a1) / i;
/* 179 */       float f10 = (this.a2 - this.last_a2) / i;
/* 180 */       float f11 = (this.b1 - this.last_b1) / i;
/* 181 */       float f12 = (this.b2 - this.last_b2) / i;
/* 182 */       float f13 = (this.gain - this.last_gain) / i;
/* 183 */       float f14 = (this.wet - this.last_wet) / i;
/* 184 */       float f15 = this.x1;
/* 185 */       float f16 = this.x2;
/* 186 */       float f17 = this.y1;
/* 187 */       float f18 = this.y2;
/* 188 */       float f19 = this.xx1;
/* 189 */       float f20 = this.xx2;
/* 190 */       float f21 = this.yy1;
/* 191 */       float f22 = this.yy2;
/*     */       
/* 193 */       if (f14 != 0.0F) {
/* 194 */         for (byte b = 0; b < i; b++) {
/* 195 */           f1 += f8;
/* 196 */           f2 += f9;
/* 197 */           f3 += f10;
/* 198 */           f4 += f11;
/* 199 */           f5 += f12;
/* 200 */           f6 += f13;
/* 201 */           f7 += f14;
/* 202 */           float f23 = arrayOfFloat[b];
/* 203 */           float f24 = f1 * f23 + f2 * f15 + f3 * f16 - f4 * f17 - f5 * f18;
/* 204 */           float f25 = f24 * f6 * f7 + f23 * (1.0F - f7);
/* 205 */           f16 = f15;
/* 206 */           f15 = f23;
/* 207 */           f18 = f17;
/* 208 */           f17 = f24;
/* 209 */           float f26 = f1 * f25 + f2 * f19 + f3 * f20 - f4 * f21 - f5 * f22;
/* 210 */           arrayOfFloat[b] = f26 * f6 * f7 + f25 * (1.0F - f7);
/* 211 */           f20 = f19;
/* 212 */           f19 = f25;
/* 213 */           f22 = f21;
/* 214 */           f21 = f26;
/*     */         } 
/* 216 */       } else if (f8 == 0.0F && f9 == 0.0F && f10 == 0.0F && f11 == 0.0F && f12 == 0.0F) {
/*     */         
/* 218 */         for (byte b = 0; b < i; b++) {
/* 219 */           float f23 = arrayOfFloat[b];
/* 220 */           float f24 = f1 * f23 + f2 * f15 + f3 * f16 - f4 * f17 - f5 * f18;
/* 221 */           float f25 = f24 * f6 * f7 + f23 * (1.0F - f7);
/* 222 */           f16 = f15;
/* 223 */           f15 = f23;
/* 224 */           f18 = f17;
/* 225 */           f17 = f24;
/* 226 */           float f26 = f1 * f25 + f2 * f19 + f3 * f20 - f4 * f21 - f5 * f22;
/* 227 */           arrayOfFloat[b] = f26 * f6 * f7 + f25 * (1.0F - f7);
/* 228 */           f20 = f19;
/* 229 */           f19 = f25;
/* 230 */           f22 = f21;
/* 231 */           f21 = f26;
/*     */         } 
/*     */       } else {
/* 234 */         for (byte b = 0; b < i; b++) {
/* 235 */           f1 += f8;
/* 236 */           f2 += f9;
/* 237 */           f3 += f10;
/* 238 */           f4 += f11;
/* 239 */           f5 += f12;
/* 240 */           f6 += f13;
/* 241 */           float f23 = arrayOfFloat[b];
/* 242 */           float f24 = f1 * f23 + f2 * f15 + f3 * f16 - f4 * f17 - f5 * f18;
/* 243 */           float f25 = f24 * f6 * f7 + f23 * (1.0F - f7);
/* 244 */           f16 = f15;
/* 245 */           f15 = f23;
/* 246 */           f18 = f17;
/* 247 */           f17 = f24;
/* 248 */           float f26 = f1 * f25 + f2 * f19 + f3 * f20 - f4 * f21 - f5 * f22;
/* 249 */           arrayOfFloat[b] = f26 * f6 * f7 + f25 * (1.0F - f7);
/* 250 */           f20 = f19;
/* 251 */           f19 = f25;
/* 252 */           f22 = f21;
/* 253 */           f21 = f26;
/*     */         } 
/*     */       } 
/*     */       
/* 257 */       if (Math.abs(f15) < 1.0E-8D)
/* 258 */         f15 = 0.0F; 
/* 259 */       if (Math.abs(f16) < 1.0E-8D)
/* 260 */         f16 = 0.0F; 
/* 261 */       if (Math.abs(f17) < 1.0E-8D)
/* 262 */         f17 = 0.0F; 
/* 263 */       if (Math.abs(f18) < 1.0E-8D)
/* 264 */         f18 = 0.0F; 
/* 265 */       this.x1 = f15;
/* 266 */       this.x2 = f16;
/* 267 */       this.y1 = f17;
/* 268 */       this.y2 = f18;
/* 269 */       this.xx1 = f19;
/* 270 */       this.xx2 = f20;
/* 271 */       this.yy1 = f21;
/* 272 */       this.yy2 = f22;
/*     */     } 
/*     */     
/* 275 */     this.last_a0 = this.a0;
/* 276 */     this.last_a1 = this.a1;
/* 277 */     this.last_a2 = this.a2;
/* 278 */     this.last_b1 = this.b1;
/* 279 */     this.last_b2 = this.b2;
/* 280 */     this.last_gain = this.gain;
/* 281 */     this.last_wet = this.wet;
/*     */   }
/*     */ 
/*     */   
/*     */   private double sinh(double paramDouble) {
/* 286 */     return (Math.exp(paramDouble) - Math.exp(-paramDouble)) * 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void filter2calc() {
/* 291 */     double d = this.resonancedB;
/* 292 */     if (d < 0.0D)
/* 293 */       d = 0.0D; 
/* 294 */     if (d > 30.0D)
/* 295 */       d = 30.0D; 
/* 296 */     if (this.filtertype == 3 || this.filtertype == 19) {
/* 297 */       d *= 0.6D;
/*     */     }
/* 299 */     if (this.filtertype == 33) {
/* 300 */       this.wet = 1.0F;
/* 301 */       double d1 = this.cutoff / this.samplerate;
/* 302 */       if (d1 > 0.45D) {
/* 303 */         d1 = 0.45D;
/*     */       }
/* 305 */       double d2 = Math.PI * Math.pow(10.0D, -(d / 20.0D));
/*     */       
/* 307 */       double d3 = 6.283185307179586D * d1;
/* 308 */       double d4 = Math.cos(d3);
/* 309 */       double d5 = Math.sin(d3);
/* 310 */       double d6 = d5 * sinh(Math.log(2.0D) * d2 * d3 / d5 * 2.0D);
/*     */       
/* 312 */       double d7 = d6;
/* 313 */       double d8 = 0.0D;
/* 314 */       double d9 = -d6;
/* 315 */       double d10 = 1.0D + d6;
/* 316 */       double d11 = -2.0D * d4;
/* 317 */       double d12 = 1.0D - d6;
/*     */       
/* 319 */       double d13 = 1.0D / d10;
/* 320 */       this.b1 = (float)(d11 * d13);
/* 321 */       this.b2 = (float)(d12 * d13);
/* 322 */       this.a0 = (float)(d7 * d13);
/* 323 */       this.a1 = (float)(d8 * d13);
/* 324 */       this.a2 = (float)(d9 * d13);
/*     */     } 
/*     */     
/* 327 */     if (this.filtertype == 49) {
/* 328 */       this.wet = 1.0F;
/* 329 */       double d1 = this.cutoff / this.samplerate;
/* 330 */       if (d1 > 0.45D) {
/* 331 */         d1 = 0.45D;
/*     */       }
/* 333 */       double d2 = Math.PI * Math.pow(10.0D, -(d / 20.0D));
/*     */       
/* 335 */       double d3 = 6.283185307179586D * d1;
/* 336 */       double d4 = Math.cos(d3);
/* 337 */       double d5 = Math.sin(d3);
/* 338 */       double d6 = d5 * sinh(Math.log(2.0D) * d2 * d3 / d5 * 2.0D);
/*     */       
/* 340 */       double d7 = 1.0D;
/* 341 */       double d8 = -2.0D * d4;
/* 342 */       double d9 = 1.0D;
/* 343 */       double d10 = 1.0D + d6;
/* 344 */       double d11 = -2.0D * d4;
/* 345 */       double d12 = 1.0D - d6;
/*     */       
/* 347 */       double d13 = 1.0D / d10;
/* 348 */       this.b1 = (float)(d11 * d13);
/* 349 */       this.b2 = (float)(d12 * d13);
/* 350 */       this.a0 = (float)(d7 * d13);
/* 351 */       this.a1 = (float)(d8 * d13);
/* 352 */       this.a2 = (float)(d9 * d13);
/*     */     } 
/*     */     
/* 355 */     if (this.filtertype == 1 || this.filtertype == 3) {
/* 356 */       double d1 = this.cutoff / this.samplerate;
/* 357 */       if (d1 > 0.45D) {
/* 358 */         if (this.wet == 0.0F)
/* 359 */           if (d < 1.0E-5D) {
/* 360 */             this.wet = 0.0F;
/*     */           } else {
/* 362 */             this.wet = 1.0F;
/*     */           }  
/* 364 */         d1 = 0.45D;
/*     */       } else {
/* 366 */         this.wet = 1.0F;
/*     */       } 
/* 368 */       double d2 = 1.0D / Math.tan(Math.PI * d1);
/* 369 */       double d3 = d2 * d2;
/* 370 */       double d4 = Math.pow(10.0D, -(d / 20.0D));
/* 371 */       double d5 = Math.sqrt(2.0D) * d4;
/* 372 */       double d6 = 1.0D / (1.0D + d5 * d2 + d3);
/* 373 */       double d7 = 2.0D * d6;
/* 374 */       double d8 = d6;
/* 375 */       double d9 = 2.0D * d6 * (1.0D - d3);
/* 376 */       double d10 = d6 * (1.0D - d5 * d2 + d3);
/*     */       
/* 378 */       this.a0 = (float)d6;
/* 379 */       this.a1 = (float)d7;
/* 380 */       this.a2 = (float)d8;
/* 381 */       this.b1 = (float)d9;
/* 382 */       this.b2 = (float)d10;
/*     */     } 
/*     */ 
/*     */     
/* 386 */     if (this.filtertype == 17 || this.filtertype == 19) {
/* 387 */       double d1 = this.cutoff / this.samplerate;
/* 388 */       if (d1 > 0.45D)
/* 389 */         d1 = 0.45D; 
/* 390 */       if (d1 < 1.0E-4D)
/* 391 */         d1 = 1.0E-4D; 
/* 392 */       this.wet = 1.0F;
/* 393 */       double d2 = Math.tan(Math.PI * d1);
/* 394 */       double d3 = d2 * d2;
/* 395 */       double d4 = Math.pow(10.0D, -(d / 20.0D));
/* 396 */       double d5 = Math.sqrt(2.0D) * d4;
/* 397 */       double d6 = 1.0D / (1.0D + d5 * d2 + d3);
/* 398 */       double d7 = -2.0D * d6;
/* 399 */       double d8 = d6;
/* 400 */       double d9 = 2.0D * d6 * (d3 - 1.0D);
/* 401 */       double d10 = d6 * (1.0D - d5 * d2 + d3);
/*     */       
/* 403 */       this.a0 = (float)d6;
/* 404 */       this.a1 = (float)d7;
/* 405 */       this.a2 = (float)d8;
/* 406 */       this.b1 = (float)d9;
/* 407 */       this.b2 = (float)d10;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void filter2(SoftAudioBuffer paramSoftAudioBuffer) {
/* 415 */     float[] arrayOfFloat = paramSoftAudioBuffer.array();
/*     */     
/* 417 */     if (this.dirty) {
/* 418 */       filter2calc();
/* 419 */       this.dirty = false;
/*     */     } 
/* 421 */     if (!this.last_set) {
/* 422 */       this.last_a0 = this.a0;
/* 423 */       this.last_a1 = this.a1;
/* 424 */       this.last_a2 = this.a2;
/* 425 */       this.last_b1 = this.b1;
/* 426 */       this.last_b2 = this.b2;
/* 427 */       this.last_q = this.q;
/* 428 */       this.last_gain = this.gain;
/* 429 */       this.last_wet = this.wet;
/* 430 */       this.last_set = true;
/*     */     } 
/*     */     
/* 433 */     if (this.wet > 0.0F || this.last_wet > 0.0F) {
/*     */       
/* 435 */       int i = arrayOfFloat.length;
/* 436 */       float f1 = this.last_a0;
/* 437 */       float f2 = this.last_a1;
/* 438 */       float f3 = this.last_a2;
/* 439 */       float f4 = this.last_b1;
/* 440 */       float f5 = this.last_b2;
/* 441 */       float f6 = this.last_gain;
/* 442 */       float f7 = this.last_wet;
/* 443 */       float f8 = (this.a0 - this.last_a0) / i;
/* 444 */       float f9 = (this.a1 - this.last_a1) / i;
/* 445 */       float f10 = (this.a2 - this.last_a2) / i;
/* 446 */       float f11 = (this.b1 - this.last_b1) / i;
/* 447 */       float f12 = (this.b2 - this.last_b2) / i;
/* 448 */       float f13 = (this.gain - this.last_gain) / i;
/* 449 */       float f14 = (this.wet - this.last_wet) / i;
/* 450 */       float f15 = this.x1;
/* 451 */       float f16 = this.x2;
/* 452 */       float f17 = this.y1;
/* 453 */       float f18 = this.y2;
/*     */       
/* 455 */       if (f14 != 0.0F) {
/* 456 */         for (byte b = 0; b < i; b++) {
/* 457 */           f1 += f8;
/* 458 */           f2 += f9;
/* 459 */           f3 += f10;
/* 460 */           f4 += f11;
/* 461 */           f5 += f12;
/* 462 */           f6 += f13;
/* 463 */           f7 += f14;
/* 464 */           float f19 = arrayOfFloat[b];
/* 465 */           float f20 = f1 * f19 + f2 * f15 + f3 * f16 - f4 * f17 - f5 * f18;
/* 466 */           arrayOfFloat[b] = f20 * f6 * f7 + f19 * (1.0F - f7);
/* 467 */           f16 = f15;
/* 468 */           f15 = f19;
/* 469 */           f18 = f17;
/* 470 */           f17 = f20;
/*     */         } 
/* 472 */       } else if (f8 == 0.0F && f9 == 0.0F && f10 == 0.0F && f11 == 0.0F && f12 == 0.0F) {
/*     */         
/* 474 */         for (byte b = 0; b < i; b++) {
/* 475 */           float f19 = arrayOfFloat[b];
/* 476 */           float f20 = f1 * f19 + f2 * f15 + f3 * f16 - f4 * f17 - f5 * f18;
/* 477 */           arrayOfFloat[b] = f20 * f6;
/* 478 */           f16 = f15;
/* 479 */           f15 = f19;
/* 480 */           f18 = f17;
/* 481 */           f17 = f20;
/*     */         } 
/*     */       } else {
/* 484 */         for (byte b = 0; b < i; b++) {
/* 485 */           f1 += f8;
/* 486 */           f2 += f9;
/* 487 */           f3 += f10;
/* 488 */           f4 += f11;
/* 489 */           f5 += f12;
/* 490 */           f6 += f13;
/* 491 */           float f19 = arrayOfFloat[b];
/* 492 */           float f20 = f1 * f19 + f2 * f15 + f3 * f16 - f4 * f17 - f5 * f18;
/* 493 */           arrayOfFloat[b] = f20 * f6;
/* 494 */           f16 = f15;
/* 495 */           f15 = f19;
/* 496 */           f18 = f17;
/* 497 */           f17 = f20;
/*     */         } 
/*     */       } 
/*     */       
/* 501 */       if (Math.abs(f15) < 1.0E-8D)
/* 502 */         f15 = 0.0F; 
/* 503 */       if (Math.abs(f16) < 1.0E-8D)
/* 504 */         f16 = 0.0F; 
/* 505 */       if (Math.abs(f17) < 1.0E-8D)
/* 506 */         f17 = 0.0F; 
/* 507 */       if (Math.abs(f18) < 1.0E-8D)
/* 508 */         f18 = 0.0F; 
/* 509 */       this.x1 = f15;
/* 510 */       this.x2 = f16;
/* 511 */       this.y1 = f17;
/* 512 */       this.y2 = f18;
/*     */     } 
/*     */     
/* 515 */     this.last_a0 = this.a0;
/* 516 */     this.last_a1 = this.a1;
/* 517 */     this.last_a2 = this.a2;
/* 518 */     this.last_b1 = this.b1;
/* 519 */     this.last_b2 = this.b2;
/* 520 */     this.last_q = this.q;
/* 521 */     this.last_gain = this.gain;
/* 522 */     this.last_wet = this.wet;
/*     */   }
/*     */ 
/*     */   
/*     */   public void filter1calc() {
/* 527 */     if (this.cutoff < 120.0D)
/* 528 */       this.cutoff = 120.0D; 
/* 529 */     double d = 7.3303828583761845D * this.cutoff / this.samplerate;
/* 530 */     if (d > 1.0D)
/* 531 */       d = 1.0D; 
/* 532 */     this.a0 = (float)(Math.sqrt(1.0D - Math.cos(d)) * Math.sqrt(1.5707963267948966D));
/* 533 */     if (this.resonancedB < 0.0D)
/* 534 */       this.resonancedB = 0.0D; 
/* 535 */     if (this.resonancedB > 20.0D)
/* 536 */       this.resonancedB = 20.0D; 
/* 537 */     this.q = (float)(Math.sqrt(0.5D) * Math.pow(10.0D, -(this.resonancedB / 20.0D)));
/* 538 */     this.gain = (float)Math.pow(10.0D, -this.resonancedB / 40.0D);
/* 539 */     if (this.wet == 0.0F && (
/* 540 */       this.resonancedB > 1.0E-5D || d < 0.9999999D)) {
/* 541 */       this.wet = 1.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   public void filter1(SoftAudioBuffer paramSoftAudioBuffer) {
/* 546 */     if (this.dirty) {
/* 547 */       filter1calc();
/* 548 */       this.dirty = false;
/*     */     } 
/* 550 */     if (!this.last_set) {
/* 551 */       this.last_a0 = this.a0;
/* 552 */       this.last_q = this.q;
/* 553 */       this.last_gain = this.gain;
/* 554 */       this.last_wet = this.wet;
/* 555 */       this.last_set = true;
/*     */     } 
/*     */     
/* 558 */     if (this.wet > 0.0F || this.last_wet > 0.0F) {
/*     */       
/* 560 */       float[] arrayOfFloat = paramSoftAudioBuffer.array();
/* 561 */       int i = arrayOfFloat.length;
/* 562 */       float f1 = this.last_a0;
/* 563 */       float f2 = this.last_q;
/* 564 */       float f3 = this.last_gain;
/* 565 */       float f4 = this.last_wet;
/* 566 */       float f5 = (this.a0 - this.last_a0) / i;
/* 567 */       float f6 = (this.q - this.last_q) / i;
/* 568 */       float f7 = (this.gain - this.last_gain) / i;
/* 569 */       float f8 = (this.wet - this.last_wet) / i;
/* 570 */       float f9 = this.y2;
/* 571 */       float f10 = this.y1;
/*     */       
/* 573 */       if (f8 != 0.0F) {
/* 574 */         for (byte b = 0; b < i; b++) {
/* 575 */           f1 += f5;
/* 576 */           f2 += f6;
/* 577 */           f3 += f7;
/* 578 */           f4 += f8;
/* 579 */           float f = 1.0F - f2 * f1;
/* 580 */           f10 = f * f10 + f1 * (arrayOfFloat[b] - f9);
/* 581 */           f9 = f * f9 + f1 * f10;
/* 582 */           arrayOfFloat[b] = f9 * f3 * f4 + arrayOfFloat[b] * (1.0F - f4);
/*     */         } 
/* 584 */       } else if (f5 == 0.0F && f6 == 0.0F) {
/* 585 */         float f = 1.0F - f2 * f1;
/* 586 */         for (byte b = 0; b < i; b++) {
/* 587 */           f10 = f * f10 + f1 * (arrayOfFloat[b] - f9);
/* 588 */           f9 = f * f9 + f1 * f10;
/* 589 */           arrayOfFloat[b] = f9 * f3;
/*     */         } 
/*     */       } else {
/* 592 */         for (byte b = 0; b < i; b++) {
/* 593 */           f1 += f5;
/* 594 */           f2 += f6;
/* 595 */           f3 += f7;
/* 596 */           float f = 1.0F - f2 * f1;
/* 597 */           f10 = f * f10 + f1 * (arrayOfFloat[b] - f9);
/* 598 */           f9 = f * f9 + f1 * f10;
/* 599 */           arrayOfFloat[b] = f9 * f3;
/*     */         } 
/*     */       } 
/*     */       
/* 603 */       if (Math.abs(f9) < 1.0E-8D)
/* 604 */         f9 = 0.0F; 
/* 605 */       if (Math.abs(f10) < 1.0E-8D)
/* 606 */         f10 = 0.0F; 
/* 607 */       this.y2 = f9;
/* 608 */       this.y1 = f10;
/*     */     } 
/*     */     
/* 611 */     this.last_a0 = this.a0;
/* 612 */     this.last_q = this.q;
/* 613 */     this.last_gain = this.gain;
/* 614 */     this.last_wet = this.wet;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\SoftFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */