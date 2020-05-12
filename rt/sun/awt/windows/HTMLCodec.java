/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Arrays;
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
/*     */ class HTMLCodec
/*     */   extends InputStream
/*     */ {
/*     */   public static final String ENCODING = "UTF-8";
/*     */   public static final String VERSION = "Version:";
/*     */   public static final String START_HTML = "StartHTML:";
/*     */   public static final String END_HTML = "EndHTML:";
/*     */   public static final String START_FRAGMENT = "StartFragment:";
/*     */   public static final String END_FRAGMENT = "EndFragment:";
/*     */   public static final String START_SELECTION = "StartSelection:";
/*     */   public static final String END_SELECTION = "EndSelection:";
/*     */   public static final String START_FRAGMENT_CMT = "<!--StartFragment-->";
/*     */   public static final String END_FRAGMENT_CMT = "<!--EndFragment-->";
/*     */   public static final String SOURCE_URL = "SourceURL:";
/*     */   public static final String DEF_SOURCE_URL = "about:blank";
/*     */   public static final String EOLN = "\r\n";
/*     */   private static final String VERSION_NUM = "1.0";
/*     */   private static final int PADDED_WIDTH = 10;
/*     */   private final BufferedInputStream bufferedStream;
/*     */   
/*     */   private static String toPaddedString(int paramInt1, int paramInt2) {
/* 572 */     String str = "" + paramInt1;
/* 573 */     int i = str.length();
/* 574 */     if (paramInt1 >= 0 && i < paramInt2) {
/* 575 */       char[] arrayOfChar = new char[paramInt2 - i];
/* 576 */       Arrays.fill(arrayOfChar, '0');
/* 577 */       StringBuffer stringBuffer = new StringBuffer(paramInt2);
/* 578 */       stringBuffer.append(arrayOfChar);
/* 579 */       stringBuffer.append(str);
/* 580 */       str = stringBuffer.toString();
/*     */     } 
/* 582 */     return str;
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
/*     */   public static byte[] convertToHTMLFormat(byte[] paramArrayOfbyte) {
/* 614 */     String str1 = "";
/* 615 */     String str2 = "";
/*     */ 
/*     */ 
/*     */     
/* 619 */     String str3 = new String(paramArrayOfbyte);
/* 620 */     String str4 = str3.toUpperCase();
/* 621 */     if (-1 == str4.indexOf("<HTML")) {
/* 622 */       str1 = "<HTML>";
/* 623 */       str2 = "</HTML>";
/* 624 */       if (-1 == str4.indexOf("<BODY")) {
/* 625 */         str1 = str1 + "<BODY>";
/* 626 */         str2 = "</BODY>" + str2;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 631 */     str3 = "about:blank";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 638 */     int i = "Version:".length() + "1.0".length() + "\r\n".length() + "StartHTML:".length() + 10 + "\r\n".length() + "EndHTML:".length() + 10 + "\r\n".length() + "StartFragment:".length() + 10 + "\r\n".length() + "EndFragment:".length() + 10 + "\r\n".length() + "SourceURL:".length() + str3.length() + "\r\n".length();
/*     */     
/* 640 */     int j = i + str1.length();
/* 641 */     int k = j + paramArrayOfbyte.length - 1;
/* 642 */     int m = k + str2.length();
/*     */ 
/*     */ 
/*     */     
/* 646 */     StringBuilder stringBuilder = new StringBuilder(j + "<!--StartFragment-->".length());
/*     */ 
/*     */     
/* 649 */     stringBuilder.append("Version:");
/* 650 */     stringBuilder.append("1.0");
/* 651 */     stringBuilder.append("\r\n");
/*     */     
/* 653 */     stringBuilder.append("StartHTML:");
/* 654 */     stringBuilder.append(toPaddedString(i, 10));
/* 655 */     stringBuilder.append("\r\n");
/*     */     
/* 657 */     stringBuilder.append("EndHTML:");
/* 658 */     stringBuilder.append(toPaddedString(m, 10));
/* 659 */     stringBuilder.append("\r\n");
/*     */     
/* 661 */     stringBuilder.append("StartFragment:");
/* 662 */     stringBuilder.append(toPaddedString(j, 10));
/* 663 */     stringBuilder.append("\r\n");
/*     */     
/* 665 */     stringBuilder.append("EndFragment:");
/* 666 */     stringBuilder.append(toPaddedString(k, 10));
/* 667 */     stringBuilder.append("\r\n");
/*     */     
/* 669 */     stringBuilder.append("SourceURL:");
/* 670 */     stringBuilder.append(str3);
/* 671 */     stringBuilder.append("\r\n");
/*     */ 
/*     */     
/* 674 */     stringBuilder.append(str1);
/*     */     
/* 676 */     byte[] arrayOfByte1 = null, arrayOfByte2 = null;
/*     */     
/*     */     try {
/* 679 */       arrayOfByte1 = stringBuilder.toString().getBytes("UTF-8");
/* 680 */       arrayOfByte2 = str2.getBytes("UTF-8");
/* 681 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */     
/* 684 */     byte[] arrayOfByte3 = new byte[arrayOfByte1.length + paramArrayOfbyte.length + arrayOfByte2.length];
/*     */ 
/*     */     
/* 687 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
/* 688 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte3, arrayOfByte1.length, paramArrayOfbyte.length - 1);
/*     */     
/* 690 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length + paramArrayOfbyte.length - 1, arrayOfByte2.length);
/*     */ 
/*     */     
/* 693 */     arrayOfByte3[arrayOfByte3.length - 1] = 0;
/*     */     
/* 695 */     return arrayOfByte3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean descriptionParsed = false;
/*     */   
/*     */   private boolean closed = false;
/*     */   
/*     */   public static final int BYTE_BUFFER_LEN = 8192;
/*     */   
/*     */   public static final int CHAR_BUFFER_LEN = 2730;
/*     */   
/*     */   private static final String FAILURE_MSG = "Unable to parse HTML description: ";
/*     */   
/*     */   private static final String INVALID_MSG = " invalid";
/*     */   
/*     */   private long iHTMLStart;
/*     */   
/*     */   private long iHTMLEnd;
/*     */   
/*     */   private long iFragStart;
/*     */   
/*     */   private long iFragEnd;
/*     */   
/*     */   private long iSelStart;
/*     */   
/*     */   private long iSelEnd;
/*     */   
/*     */   private String stBaseURL;
/*     */   
/*     */   private String stVersion;
/*     */   
/*     */   private long iStartOffset;
/*     */   
/*     */   private long iEndOffset;
/*     */   
/*     */   private long iReadCount;
/*     */   
/*     */   private EHTMLReadMode readMode;
/*     */ 
/*     */   
/*     */   public HTMLCodec(InputStream paramInputStream, EHTMLReadMode paramEHTMLReadMode) throws IOException {
/* 738 */     this.bufferedStream = new BufferedInputStream(paramInputStream, 8192);
/* 739 */     this.readMode = paramEHTMLReadMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String getBaseURL() throws IOException {
/* 744 */     if (!this.descriptionParsed) {
/* 745 */       parseDescription();
/*     */     }
/* 747 */     return this.stBaseURL;
/*     */   }
/*     */   
/*     */   public synchronized String getVersion() throws IOException {
/* 751 */     if (!this.descriptionParsed) {
/* 752 */       parseDescription();
/*     */     }
/* 754 */     return this.stVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseDescription() throws IOException {
/* 763 */     this.stBaseURL = null;
/* 764 */     this.stVersion = null;
/*     */ 
/*     */ 
/*     */     
/* 768 */     this.iHTMLEnd = this.iHTMLStart = this.iFragEnd = this.iFragStart = this.iSelEnd = this.iSelStart = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 775 */     this.bufferedStream.mark(8192);
/* 776 */     String[] arrayOfString = { "Version:", "StartHTML:", "EndHTML:", "StartFragment:", "EndFragment:", "StartSelection:", "EndSelection:", "SourceURL:" };
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
/* 788 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.bufferedStream, "UTF-8"), 2730);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 795 */     long l1 = 0L;
/* 796 */     long l2 = "\r\n".length();
/* 797 */     int i = arrayOfString.length;
/* 798 */     boolean bool = true;
/*     */     int j;
/* 800 */     for (j = 0; j < i; j++) {
/* 801 */       String str = bufferedReader.readLine();
/* 802 */       if (null == str) {
/*     */         break;
/*     */       }
/*     */       
/* 806 */       while (j < i) {
/* 807 */         if (!str.startsWith(arrayOfString[j])) {
/*     */           j++; continue;
/*     */         } 
/* 810 */         l1 += str.length() + l2;
/* 811 */         String str1 = str.substring(arrayOfString[j].length()).trim();
/* 812 */         if (null != str1) {
/*     */           try {
/* 814 */             switch (j) {
/*     */               case 0:
/* 816 */                 this.stVersion = str1;
/*     */                 break;
/*     */               case 1:
/* 819 */                 this.iHTMLStart = Integer.parseInt(str1);
/*     */                 break;
/*     */               case 2:
/* 822 */                 this.iHTMLEnd = Integer.parseInt(str1);
/*     */                 break;
/*     */               case 3:
/* 825 */                 this.iFragStart = Integer.parseInt(str1);
/*     */                 break;
/*     */               case 4:
/* 828 */                 this.iFragEnd = Integer.parseInt(str1);
/*     */                 break;
/*     */               case 5:
/* 831 */                 this.iSelStart = Integer.parseInt(str1);
/*     */                 break;
/*     */               case 6:
/* 834 */                 this.iSelEnd = Integer.parseInt(str1);
/*     */                 break;
/*     */               case 7:
/* 837 */                 this.stBaseURL = str1;
/*     */                 break;
/*     */             } 
/* 840 */           } catch (NumberFormatException numberFormatException) {
/* 841 */             throw new IOException("Unable to parse HTML description: " + arrayOfString[j] + " value " + numberFormatException + " invalid");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 849 */     if (-1L == this.iHTMLStart)
/* 850 */       this.iHTMLStart = l1; 
/* 851 */     if (-1L == this.iFragStart)
/* 852 */       this.iFragStart = this.iHTMLStart; 
/* 853 */     if (-1L == this.iFragEnd)
/* 854 */       this.iFragEnd = this.iHTMLEnd; 
/* 855 */     if (-1L == this.iSelStart)
/* 856 */       this.iSelStart = this.iFragStart; 
/* 857 */     if (-1L == this.iSelEnd) {
/* 858 */       this.iSelEnd = this.iFragEnd;
/*     */     }
/*     */     
/* 861 */     switch (this.readMode) {
/*     */       case HTML_READ_ALL:
/* 863 */         this.iStartOffset = this.iHTMLStart;
/* 864 */         this.iEndOffset = this.iHTMLEnd;
/*     */         break;
/*     */       case HTML_READ_FRAGMENT:
/* 867 */         this.iStartOffset = this.iFragStart;
/* 868 */         this.iEndOffset = this.iFragEnd;
/*     */         break;
/*     */       
/*     */       default:
/* 872 */         this.iStartOffset = this.iSelStart;
/* 873 */         this.iEndOffset = this.iSelEnd;
/*     */         break;
/*     */     } 
/*     */     
/* 877 */     this.bufferedStream.reset();
/* 878 */     if (-1L == this.iStartOffset) {
/* 879 */       throw new IOException("Unable to parse HTML description: invalid HTML format.");
/*     */     }
/*     */     
/* 882 */     j = 0;
/* 883 */     while (j < this.iStartOffset) {
/* 884 */       j = (int)(j + this.bufferedStream.skip(this.iStartOffset - j));
/*     */     }
/*     */     
/* 887 */     this.iReadCount = j;
/*     */     
/* 889 */     if (this.iStartOffset != this.iReadCount) {
/* 890 */       throw new IOException("Unable to parse HTML description: Byte stream ends in description.");
/*     */     }
/* 892 */     this.descriptionParsed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read() throws IOException {
/* 897 */     if (this.closed) {
/* 898 */       throw new IOException("Stream closed");
/*     */     }
/*     */     
/* 901 */     if (!this.descriptionParsed) {
/* 902 */       parseDescription();
/*     */     }
/* 904 */     if (-1L != this.iEndOffset && this.iReadCount >= this.iEndOffset) {
/* 905 */       return -1;
/*     */     }
/*     */     
/* 908 */     int i = this.bufferedStream.read();
/* 909 */     if (i == -1) {
/* 910 */       return -1;
/*     */     }
/* 912 */     this.iReadCount++;
/* 913 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 918 */     if (!this.closed) {
/* 919 */       this.closed = true;
/* 920 */       this.bufferedStream.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\HTMLCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */