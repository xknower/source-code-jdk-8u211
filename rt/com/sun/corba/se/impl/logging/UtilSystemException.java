/*     */ package com.sun.corba.se.impl.logging;
/*     */ 
/*     */ import com.sun.corba.se.spi.logging.LogWrapperBase;
/*     */ import com.sun.corba.se.spi.logging.LogWrapperFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.DATA_CONVERSION;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ import org.omg.CORBA.INV_OBJREF;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.UNKNOWN;
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
/*     */ public class UtilSystemException
/*     */   extends LogWrapperBase
/*     */ {
/*     */   public UtilSystemException(Logger paramLogger) {
/*  34 */     super(paramLogger);
/*     */   }
/*     */   
/*  37 */   private static LogWrapperFactory factory = new LogWrapperFactory()
/*     */     {
/*     */       public LogWrapperBase create(Logger param1Logger) {
/*  40 */         return new UtilSystemException(param1Logger);
/*     */       }
/*     */     };
/*     */   public static final int STUB_FACTORY_COULD_NOT_MAKE_STUB = 1398080889; public static final int ERROR_IN_MAKE_STUB_FROM_REPOSITORY_ID = 1398080890; public static final int CLASS_CAST_EXCEPTION_IN_LOAD_STUB = 1398080891; public static final int EXCEPTION_IN_LOAD_STUB = 1398080892; public static final int NO_POA = 1398080890; public static final int CONNECT_WRONG_ORB = 1398080891; public static final int CONNECT_NO_TIE = 1398080892;
/*     */   
/*     */   public static UtilSystemException get(ORB paramORB, String paramString) {
/*  46 */     return (UtilSystemException)paramORB
/*  47 */       .getLogWrapper(paramString, "UTIL", factory);
/*     */   }
/*     */   public static final int CONNECT_TIE_WRONG_ORB = 1398080893; public static final int CONNECT_TIE_NO_SERVANT = 1398080894; public static final int LOAD_TIE_FAILED = 1398080895; public static final int BAD_HEX_DIGIT = 1398080889; public static final int UNABLE_LOCATE_VALUE_HELPER = 1398080890; public static final int INVALID_INDIRECTION = 1398080891; public static final int OBJECT_NOT_CONNECTED = 1398080889; public static final int COULD_NOT_LOAD_STUB = 1398080890; public static final int OBJECT_NOT_EXPORTED = 1398080891; public static final int ERROR_SET_OBJECT_FIELD = 1398080889; public static final int ERROR_SET_BOOLEAN_FIELD = 1398080890; public static final int ERROR_SET_BYTE_FIELD = 1398080891; public static final int ERROR_SET_CHAR_FIELD = 1398080892; public static final int ERROR_SET_SHORT_FIELD = 1398080893; public static final int ERROR_SET_INT_FIELD = 1398080894; public static final int ERROR_SET_LONG_FIELD = 1398080895; public static final int ERROR_SET_FLOAT_FIELD = 1398080896; public static final int ERROR_SET_DOUBLE_FIELD = 1398080897; public static final int ILLEGAL_FIELD_ACCESS = 1398080898;
/*     */   public static final int BAD_BEGIN_UNMARSHAL_CUSTOM_VALUE = 1398080899;
/*     */   public static final int CLASS_NOT_FOUND = 1398080900;
/*     */   public static final int UNKNOWN_SYSEX = 1398080889;
/*     */   
/*     */   public static UtilSystemException get(String paramString) {
/*  55 */     return (UtilSystemException)ORB.staticGetLogWrapper(paramString, "UTIL", factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION stubFactoryCouldNotMakeStub(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/*  67 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080889, paramCompletionStatus);
/*  68 */     if (paramThrowable != null) {
/*  69 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/*  71 */     if (this.logger.isLoggable(Level.FINE)) {
/*  72 */       Object[] arrayOfObject = null;
/*  73 */       doLog(Level.FINE, "UTIL.stubFactoryCouldNotMakeStub", arrayOfObject, UtilSystemException.class, bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/*  77 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION stubFactoryCouldNotMakeStub(CompletionStatus paramCompletionStatus) {
/*  81 */     return stubFactoryCouldNotMakeStub(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION stubFactoryCouldNotMakeStub(Throwable paramThrowable) {
/*  85 */     return stubFactoryCouldNotMakeStub(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION stubFactoryCouldNotMakeStub() {
/*  89 */     return stubFactoryCouldNotMakeStub(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION errorInMakeStubFromRepositoryId(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/*  95 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080890, paramCompletionStatus);
/*  96 */     if (paramThrowable != null) {
/*  97 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/*  99 */     if (this.logger.isLoggable(Level.FINE)) {
/* 100 */       Object[] arrayOfObject = null;
/* 101 */       doLog(Level.FINE, "UTIL.errorInMakeStubFromRepositoryId", arrayOfObject, UtilSystemException.class, bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/* 105 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION errorInMakeStubFromRepositoryId(CompletionStatus paramCompletionStatus) {
/* 109 */     return errorInMakeStubFromRepositoryId(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION errorInMakeStubFromRepositoryId(Throwable paramThrowable) {
/* 113 */     return errorInMakeStubFromRepositoryId(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION errorInMakeStubFromRepositoryId() {
/* 117 */     return errorInMakeStubFromRepositoryId(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION classCastExceptionInLoadStub(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 123 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080891, paramCompletionStatus);
/* 124 */     if (paramThrowable != null) {
/* 125 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/* 127 */     if (this.logger.isLoggable(Level.FINE)) {
/* 128 */       Object[] arrayOfObject = null;
/* 129 */       doLog(Level.FINE, "UTIL.classCastExceptionInLoadStub", arrayOfObject, UtilSystemException.class, bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/* 133 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION classCastExceptionInLoadStub(CompletionStatus paramCompletionStatus) {
/* 137 */     return classCastExceptionInLoadStub(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION classCastExceptionInLoadStub(Throwable paramThrowable) {
/* 141 */     return classCastExceptionInLoadStub(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION classCastExceptionInLoadStub() {
/* 145 */     return classCastExceptionInLoadStub(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION exceptionInLoadStub(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 151 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080892, paramCompletionStatus);
/* 152 */     if (paramThrowable != null) {
/* 153 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/* 155 */     if (this.logger.isLoggable(Level.FINE)) {
/* 156 */       Object[] arrayOfObject = null;
/* 157 */       doLog(Level.FINE, "UTIL.exceptionInLoadStub", arrayOfObject, UtilSystemException.class, bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/* 161 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION exceptionInLoadStub(CompletionStatus paramCompletionStatus) {
/* 165 */     return exceptionInLoadStub(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION exceptionInLoadStub(Throwable paramThrowable) {
/* 169 */     return exceptionInLoadStub(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION exceptionInLoadStub() {
/* 173 */     return exceptionInLoadStub(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM noPoa(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 183 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080890, paramCompletionStatus);
/* 184 */     if (paramThrowable != null) {
/* 185 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 187 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 188 */       Object[] arrayOfObject = null;
/* 189 */       doLog(Level.WARNING, "UTIL.noPoa", arrayOfObject, UtilSystemException.class, bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 193 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM noPoa(CompletionStatus paramCompletionStatus) {
/* 197 */     return noPoa(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM noPoa(Throwable paramThrowable) {
/* 201 */     return noPoa(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM noPoa() {
/* 205 */     return noPoa(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM connectWrongOrb(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 211 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080891, paramCompletionStatus);
/* 212 */     if (paramThrowable != null) {
/* 213 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 215 */     if (this.logger.isLoggable(Level.FINE)) {
/* 216 */       Object[] arrayOfObject = null;
/* 217 */       doLog(Level.FINE, "UTIL.connectWrongOrb", arrayOfObject, UtilSystemException.class, bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 221 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectWrongOrb(CompletionStatus paramCompletionStatus) {
/* 225 */     return connectWrongOrb(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectWrongOrb(Throwable paramThrowable) {
/* 229 */     return connectWrongOrb(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectWrongOrb() {
/* 233 */     return connectWrongOrb(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM connectNoTie(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 239 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080892, paramCompletionStatus);
/* 240 */     if (paramThrowable != null) {
/* 241 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 243 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 244 */       Object[] arrayOfObject = null;
/* 245 */       doLog(Level.WARNING, "UTIL.connectNoTie", arrayOfObject, UtilSystemException.class, bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 249 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectNoTie(CompletionStatus paramCompletionStatus) {
/* 253 */     return connectNoTie(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectNoTie(Throwable paramThrowable) {
/* 257 */     return connectNoTie(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectNoTie() {
/* 261 */     return connectNoTie(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM connectTieWrongOrb(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 267 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080893, paramCompletionStatus);
/* 268 */     if (paramThrowable != null) {
/* 269 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 271 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 272 */       Object[] arrayOfObject = null;
/* 273 */       doLog(Level.WARNING, "UTIL.connectTieWrongOrb", arrayOfObject, UtilSystemException.class, bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 277 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectTieWrongOrb(CompletionStatus paramCompletionStatus) {
/* 281 */     return connectTieWrongOrb(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectTieWrongOrb(Throwable paramThrowable) {
/* 285 */     return connectTieWrongOrb(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectTieWrongOrb() {
/* 289 */     return connectTieWrongOrb(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM connectTieNoServant(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 295 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080894, paramCompletionStatus);
/* 296 */     if (paramThrowable != null) {
/* 297 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 299 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 300 */       Object[] arrayOfObject = null;
/* 301 */       doLog(Level.WARNING, "UTIL.connectTieNoServant", arrayOfObject, UtilSystemException.class, bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 305 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectTieNoServant(CompletionStatus paramCompletionStatus) {
/* 309 */     return connectTieNoServant(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectTieNoServant(Throwable paramThrowable) {
/* 313 */     return connectTieNoServant(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM connectTieNoServant() {
/* 317 */     return connectTieNoServant(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM loadTieFailed(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 323 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080895, paramCompletionStatus);
/* 324 */     if (paramThrowable != null) {
/* 325 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 327 */     if (this.logger.isLoggable(Level.FINE)) {
/* 328 */       Object[] arrayOfObject = new Object[1];
/* 329 */       arrayOfObject[0] = paramObject;
/* 330 */       doLog(Level.FINE, "UTIL.loadTieFailed", arrayOfObject, UtilSystemException.class, bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 334 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM loadTieFailed(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 338 */     return loadTieFailed(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public BAD_PARAM loadTieFailed(Throwable paramThrowable, Object paramObject) {
/* 342 */     return loadTieFailed(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public BAD_PARAM loadTieFailed(Object paramObject) {
/* 346 */     return loadTieFailed(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DATA_CONVERSION badHexDigit(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 356 */     DATA_CONVERSION dATA_CONVERSION = new DATA_CONVERSION(1398080889, paramCompletionStatus);
/* 357 */     if (paramThrowable != null) {
/* 358 */       dATA_CONVERSION.initCause(paramThrowable);
/*     */     }
/* 360 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 361 */       Object[] arrayOfObject = null;
/* 362 */       doLog(Level.WARNING, "UTIL.badHexDigit", arrayOfObject, UtilSystemException.class, dATA_CONVERSION);
/*     */     } 
/*     */ 
/*     */     
/* 366 */     return dATA_CONVERSION;
/*     */   }
/*     */   
/*     */   public DATA_CONVERSION badHexDigit(CompletionStatus paramCompletionStatus) {
/* 370 */     return badHexDigit(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public DATA_CONVERSION badHexDigit(Throwable paramThrowable) {
/* 374 */     return badHexDigit(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public DATA_CONVERSION badHexDigit() {
/* 378 */     return badHexDigit(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MARSHAL unableLocateValueHelper(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 388 */     MARSHAL mARSHAL = new MARSHAL(1398080890, paramCompletionStatus);
/* 389 */     if (paramThrowable != null) {
/* 390 */       mARSHAL.initCause(paramThrowable);
/*     */     }
/* 392 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 393 */       Object[] arrayOfObject = null;
/* 394 */       doLog(Level.WARNING, "UTIL.unableLocateValueHelper", arrayOfObject, UtilSystemException.class, mARSHAL);
/*     */     } 
/*     */ 
/*     */     
/* 398 */     return mARSHAL;
/*     */   }
/*     */   
/*     */   public MARSHAL unableLocateValueHelper(CompletionStatus paramCompletionStatus) {
/* 402 */     return unableLocateValueHelper(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public MARSHAL unableLocateValueHelper(Throwable paramThrowable) {
/* 406 */     return unableLocateValueHelper(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public MARSHAL unableLocateValueHelper() {
/* 410 */     return unableLocateValueHelper(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MARSHAL invalidIndirection(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 416 */     MARSHAL mARSHAL = new MARSHAL(1398080891, paramCompletionStatus);
/* 417 */     if (paramThrowable != null) {
/* 418 */       mARSHAL.initCause(paramThrowable);
/*     */     }
/* 420 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 421 */       Object[] arrayOfObject = new Object[1];
/* 422 */       arrayOfObject[0] = paramObject;
/* 423 */       doLog(Level.WARNING, "UTIL.invalidIndirection", arrayOfObject, UtilSystemException.class, mARSHAL);
/*     */     } 
/*     */ 
/*     */     
/* 427 */     return mARSHAL;
/*     */   }
/*     */   
/*     */   public MARSHAL invalidIndirection(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 431 */     return invalidIndirection(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public MARSHAL invalidIndirection(Throwable paramThrowable, Object paramObject) {
/* 435 */     return invalidIndirection(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public MARSHAL invalidIndirection(Object paramObject) {
/* 439 */     return invalidIndirection(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INV_OBJREF objectNotConnected(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 449 */     INV_OBJREF iNV_OBJREF = new INV_OBJREF(1398080889, paramCompletionStatus);
/* 450 */     if (paramThrowable != null) {
/* 451 */       iNV_OBJREF.initCause(paramThrowable);
/*     */     }
/* 453 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 454 */       Object[] arrayOfObject = new Object[1];
/* 455 */       arrayOfObject[0] = paramObject;
/* 456 */       doLog(Level.WARNING, "UTIL.objectNotConnected", arrayOfObject, UtilSystemException.class, iNV_OBJREF);
/*     */     } 
/*     */ 
/*     */     
/* 460 */     return iNV_OBJREF;
/*     */   }
/*     */   
/*     */   public INV_OBJREF objectNotConnected(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 464 */     return objectNotConnected(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INV_OBJREF objectNotConnected(Throwable paramThrowable, Object paramObject) {
/* 468 */     return objectNotConnected(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INV_OBJREF objectNotConnected(Object paramObject) {
/* 472 */     return objectNotConnected(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INV_OBJREF couldNotLoadStub(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 478 */     INV_OBJREF iNV_OBJREF = new INV_OBJREF(1398080890, paramCompletionStatus);
/* 479 */     if (paramThrowable != null) {
/* 480 */       iNV_OBJREF.initCause(paramThrowable);
/*     */     }
/* 482 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 483 */       Object[] arrayOfObject = new Object[1];
/* 484 */       arrayOfObject[0] = paramObject;
/* 485 */       doLog(Level.WARNING, "UTIL.couldNotLoadStub", arrayOfObject, UtilSystemException.class, iNV_OBJREF);
/*     */     } 
/*     */ 
/*     */     
/* 489 */     return iNV_OBJREF;
/*     */   }
/*     */   
/*     */   public INV_OBJREF couldNotLoadStub(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 493 */     return couldNotLoadStub(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INV_OBJREF couldNotLoadStub(Throwable paramThrowable, Object paramObject) {
/* 497 */     return couldNotLoadStub(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INV_OBJREF couldNotLoadStub(Object paramObject) {
/* 501 */     return couldNotLoadStub(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INV_OBJREF objectNotExported(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 507 */     INV_OBJREF iNV_OBJREF = new INV_OBJREF(1398080891, paramCompletionStatus);
/* 508 */     if (paramThrowable != null) {
/* 509 */       iNV_OBJREF.initCause(paramThrowable);
/*     */     }
/* 511 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 512 */       Object[] arrayOfObject = new Object[1];
/* 513 */       arrayOfObject[0] = paramObject;
/* 514 */       doLog(Level.WARNING, "UTIL.objectNotExported", arrayOfObject, UtilSystemException.class, iNV_OBJREF);
/*     */     } 
/*     */ 
/*     */     
/* 518 */     return iNV_OBJREF;
/*     */   }
/*     */   
/*     */   public INV_OBJREF objectNotExported(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 522 */     return objectNotExported(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INV_OBJREF objectNotExported(Throwable paramThrowable, Object paramObject) {
/* 526 */     return objectNotExported(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INV_OBJREF objectNotExported(Object paramObject) {
/* 530 */     return objectNotExported(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetObjectField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 540 */     INTERNAL iNTERNAL = new INTERNAL(1398080889, paramCompletionStatus);
/* 541 */     if (paramThrowable != null) {
/* 542 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 544 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 545 */       Object[] arrayOfObject = new Object[3];
/* 546 */       arrayOfObject[0] = paramObject1;
/* 547 */       arrayOfObject[1] = paramObject2;
/* 548 */       arrayOfObject[2] = paramObject3;
/* 549 */       doLog(Level.WARNING, "UTIL.errorSetObjectField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 553 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetObjectField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 557 */     return errorSetObjectField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetObjectField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 561 */     return errorSetObjectField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetObjectField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 565 */     return errorSetObjectField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetBooleanField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 571 */     INTERNAL iNTERNAL = new INTERNAL(1398080890, paramCompletionStatus);
/* 572 */     if (paramThrowable != null) {
/* 573 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 575 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 576 */       Object[] arrayOfObject = new Object[3];
/* 577 */       arrayOfObject[0] = paramObject1;
/* 578 */       arrayOfObject[1] = paramObject2;
/* 579 */       arrayOfObject[2] = paramObject3;
/* 580 */       doLog(Level.WARNING, "UTIL.errorSetBooleanField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 584 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetBooleanField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 588 */     return errorSetBooleanField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetBooleanField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 592 */     return errorSetBooleanField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetBooleanField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 596 */     return errorSetBooleanField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetByteField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 602 */     INTERNAL iNTERNAL = new INTERNAL(1398080891, paramCompletionStatus);
/* 603 */     if (paramThrowable != null) {
/* 604 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 606 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 607 */       Object[] arrayOfObject = new Object[3];
/* 608 */       arrayOfObject[0] = paramObject1;
/* 609 */       arrayOfObject[1] = paramObject2;
/* 610 */       arrayOfObject[2] = paramObject3;
/* 611 */       doLog(Level.WARNING, "UTIL.errorSetByteField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 615 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetByteField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 619 */     return errorSetByteField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetByteField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 623 */     return errorSetByteField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetByteField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 627 */     return errorSetByteField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetCharField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 633 */     INTERNAL iNTERNAL = new INTERNAL(1398080892, paramCompletionStatus);
/* 634 */     if (paramThrowable != null) {
/* 635 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 637 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 638 */       Object[] arrayOfObject = new Object[3];
/* 639 */       arrayOfObject[0] = paramObject1;
/* 640 */       arrayOfObject[1] = paramObject2;
/* 641 */       arrayOfObject[2] = paramObject3;
/* 642 */       doLog(Level.WARNING, "UTIL.errorSetCharField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 646 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetCharField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 650 */     return errorSetCharField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetCharField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 654 */     return errorSetCharField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetCharField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 658 */     return errorSetCharField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetShortField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 664 */     INTERNAL iNTERNAL = new INTERNAL(1398080893, paramCompletionStatus);
/* 665 */     if (paramThrowable != null) {
/* 666 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 668 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 669 */       Object[] arrayOfObject = new Object[3];
/* 670 */       arrayOfObject[0] = paramObject1;
/* 671 */       arrayOfObject[1] = paramObject2;
/* 672 */       arrayOfObject[2] = paramObject3;
/* 673 */       doLog(Level.WARNING, "UTIL.errorSetShortField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 677 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetShortField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 681 */     return errorSetShortField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetShortField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 685 */     return errorSetShortField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetShortField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 689 */     return errorSetShortField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetIntField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 695 */     INTERNAL iNTERNAL = new INTERNAL(1398080894, paramCompletionStatus);
/* 696 */     if (paramThrowable != null) {
/* 697 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 699 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 700 */       Object[] arrayOfObject = new Object[3];
/* 701 */       arrayOfObject[0] = paramObject1;
/* 702 */       arrayOfObject[1] = paramObject2;
/* 703 */       arrayOfObject[2] = paramObject3;
/* 704 */       doLog(Level.WARNING, "UTIL.errorSetIntField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 708 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetIntField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 712 */     return errorSetIntField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetIntField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 716 */     return errorSetIntField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetIntField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 720 */     return errorSetIntField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetLongField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 726 */     INTERNAL iNTERNAL = new INTERNAL(1398080895, paramCompletionStatus);
/* 727 */     if (paramThrowable != null) {
/* 728 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 730 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 731 */       Object[] arrayOfObject = new Object[3];
/* 732 */       arrayOfObject[0] = paramObject1;
/* 733 */       arrayOfObject[1] = paramObject2;
/* 734 */       arrayOfObject[2] = paramObject3;
/* 735 */       doLog(Level.WARNING, "UTIL.errorSetLongField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 739 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetLongField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 743 */     return errorSetLongField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetLongField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 747 */     return errorSetLongField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetLongField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 751 */     return errorSetLongField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetFloatField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 757 */     INTERNAL iNTERNAL = new INTERNAL(1398080896, paramCompletionStatus);
/* 758 */     if (paramThrowable != null) {
/* 759 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 761 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 762 */       Object[] arrayOfObject = new Object[3];
/* 763 */       arrayOfObject[0] = paramObject1;
/* 764 */       arrayOfObject[1] = paramObject2;
/* 765 */       arrayOfObject[2] = paramObject3;
/* 766 */       doLog(Level.WARNING, "UTIL.errorSetFloatField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 770 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetFloatField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 774 */     return errorSetFloatField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetFloatField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 778 */     return errorSetFloatField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetFloatField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 782 */     return errorSetFloatField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL errorSetDoubleField(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 788 */     INTERNAL iNTERNAL = new INTERNAL(1398080897, paramCompletionStatus);
/* 789 */     if (paramThrowable != null) {
/* 790 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 792 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 793 */       Object[] arrayOfObject = new Object[3];
/* 794 */       arrayOfObject[0] = paramObject1;
/* 795 */       arrayOfObject[1] = paramObject2;
/* 796 */       arrayOfObject[2] = paramObject3;
/* 797 */       doLog(Level.WARNING, "UTIL.errorSetDoubleField", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 801 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetDoubleField(CompletionStatus paramCompletionStatus, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 805 */     return errorSetDoubleField(paramCompletionStatus, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetDoubleField(Throwable paramThrowable, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 809 */     return errorSetDoubleField(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */   
/*     */   public INTERNAL errorSetDoubleField(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 813 */     return errorSetDoubleField(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject1, paramObject2, paramObject3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL illegalFieldAccess(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 819 */     INTERNAL iNTERNAL = new INTERNAL(1398080898, paramCompletionStatus);
/* 820 */     if (paramThrowable != null) {
/* 821 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 823 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 824 */       Object[] arrayOfObject = new Object[1];
/* 825 */       arrayOfObject[0] = paramObject;
/* 826 */       doLog(Level.WARNING, "UTIL.illegalFieldAccess", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 830 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL illegalFieldAccess(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 834 */     return illegalFieldAccess(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL illegalFieldAccess(Throwable paramThrowable, Object paramObject) {
/* 838 */     return illegalFieldAccess(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL illegalFieldAccess(Object paramObject) {
/* 842 */     return illegalFieldAccess(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL badBeginUnmarshalCustomValue(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 848 */     INTERNAL iNTERNAL = new INTERNAL(1398080899, paramCompletionStatus);
/* 849 */     if (paramThrowable != null) {
/* 850 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 852 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 853 */       Object[] arrayOfObject = null;
/* 854 */       doLog(Level.WARNING, "UTIL.badBeginUnmarshalCustomValue", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 858 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL badBeginUnmarshalCustomValue(CompletionStatus paramCompletionStatus) {
/* 862 */     return badBeginUnmarshalCustomValue(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL badBeginUnmarshalCustomValue(Throwable paramThrowable) {
/* 866 */     return badBeginUnmarshalCustomValue(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL badBeginUnmarshalCustomValue() {
/* 870 */     return badBeginUnmarshalCustomValue(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL classNotFound(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 876 */     INTERNAL iNTERNAL = new INTERNAL(1398080900, paramCompletionStatus);
/* 877 */     if (paramThrowable != null) {
/* 878 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 880 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 881 */       Object[] arrayOfObject = new Object[1];
/* 882 */       arrayOfObject[0] = paramObject;
/* 883 */       doLog(Level.WARNING, "UTIL.classNotFound", arrayOfObject, UtilSystemException.class, iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 887 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL classNotFound(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 891 */     return classNotFound(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL classNotFound(Throwable paramThrowable, Object paramObject) {
/* 895 */     return classNotFound(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL classNotFound(Object paramObject) {
/* 899 */     return classNotFound(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UNKNOWN unknownSysex(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 909 */     UNKNOWN uNKNOWN = new UNKNOWN(1398080889, paramCompletionStatus);
/* 910 */     if (paramThrowable != null) {
/* 911 */       uNKNOWN.initCause(paramThrowable);
/*     */     }
/* 913 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 914 */       Object[] arrayOfObject = null;
/* 915 */       doLog(Level.WARNING, "UTIL.unknownSysex", arrayOfObject, UtilSystemException.class, uNKNOWN);
/*     */     } 
/*     */ 
/*     */     
/* 919 */     return uNKNOWN;
/*     */   }
/*     */   
/*     */   public UNKNOWN unknownSysex(CompletionStatus paramCompletionStatus) {
/* 923 */     return unknownSysex(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public UNKNOWN unknownSysex(Throwable paramThrowable) {
/* 927 */     return unknownSysex(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public UNKNOWN unknownSysex() {
/* 931 */     return unknownSysex(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\logging\UtilSystemException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */