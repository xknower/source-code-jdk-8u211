/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParameterGenerator;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.DSAGenParameterSpec;
/*     */ import java.security.spec.DSAParameterSpec;
/*     */ import java.security.spec.InvalidParameterSpecException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.crypto.spec.DHParameterSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ParameterCache
/*     */ {
/*     */   private static final Map<Integer, DSAParameterSpec> dsaCache;
/*     */   
/*     */   public static DSAParameterSpec getCachedDSAParameterSpec(int paramInt1, int paramInt2) {
/*  69 */     return dsaCache.get(Integer.valueOf(paramInt1 + paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DHParameterSpec getCachedDHParameterSpec(int paramInt) {
/*  77 */     return dhCache.get(Integer.valueOf(paramInt));
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
/*     */   public static DSAParameterSpec getDSAParameterSpec(int paramInt, SecureRandom paramSecureRandom) throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
/*  89 */     if (paramInt <= 1024)
/*  90 */       return getDSAParameterSpec(paramInt, 160, paramSecureRandom); 
/*  91 */     if (paramInt == 2048)
/*  92 */       return getDSAParameterSpec(paramInt, 224, paramSecureRandom); 
/*  93 */     if (paramInt == 3072) {
/*  94 */       return getDSAParameterSpec(paramInt, 256, paramSecureRandom);
/*     */     }
/*  96 */     return null;
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
/*     */   public static DSAParameterSpec getDSAParameterSpec(int paramInt1, int paramInt2, SecureRandom paramSecureRandom) throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
/* 110 */     DSAParameterSpec dSAParameterSpec = getCachedDSAParameterSpec(paramInt1, paramInt2);
/* 111 */     if (dSAParameterSpec != null) {
/* 112 */       return dSAParameterSpec;
/*     */     }
/* 114 */     dSAParameterSpec = getNewDSAParameterSpec(paramInt1, paramInt2, paramSecureRandom);
/* 115 */     dsaCache.put(Integer.valueOf(paramInt1 + paramInt2), dSAParameterSpec);
/* 116 */     return dSAParameterSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DHParameterSpec getDHParameterSpec(int paramInt, SecureRandom paramSecureRandom) throws NoSuchAlgorithmException, InvalidParameterSpecException {
/* 126 */     DHParameterSpec dHParameterSpec = getCachedDHParameterSpec(paramInt);
/* 127 */     if (dHParameterSpec != null) {
/* 128 */       return dHParameterSpec;
/*     */     }
/*     */     
/* 131 */     AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("DH");
/* 132 */     algorithmParameterGenerator.init(paramInt, paramSecureRandom);
/* 133 */     AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters();
/* 134 */     dHParameterSpec = algorithmParameters.<DHParameterSpec>getParameterSpec(DHParameterSpec.class);
/* 135 */     dhCache.put(Integer.valueOf(paramInt), dHParameterSpec);
/* 136 */     return dHParameterSpec;
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
/*     */   public static DSAParameterSpec getNewDSAParameterSpec(int paramInt1, int paramInt2, SecureRandom paramSecureRandom) throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
/* 150 */     AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("DSA");
/*     */     
/* 152 */     if (paramInt1 < 1024) {
/* 153 */       algorithmParameterGenerator.init(paramInt1, paramSecureRandom);
/*     */     } else {
/* 155 */       DSAGenParameterSpec dSAGenParameterSpec = new DSAGenParameterSpec(paramInt1, paramInt2);
/*     */       
/* 157 */       algorithmParameterGenerator.init(dSAGenParameterSpec, paramSecureRandom);
/*     */     } 
/* 159 */     AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters();
/* 160 */     return algorithmParameters.<DSAParameterSpec>getParameterSpec(DSAParameterSpec.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 165 */   private static final Map<Integer, DHParameterSpec> dhCache = new ConcurrentHashMap<>(); static {
/* 166 */     dsaCache = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     BigInteger bigInteger1 = new BigInteger("fca682ce8e12caba26efccf7110e526db078b05edecbcd1eb4a208f3ae1617ae01f35b91a47e6df63413c5e12ed0899bcd132acd50d99151bdc43ee737592e17", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     BigInteger bigInteger2 = new BigInteger("962eddcc369cba8ebb260ee6b6a126d9346e38c5", 16);
/*     */ 
/*     */     
/* 193 */     BigInteger bigInteger3 = new BigInteger("678471b27a9cf44ee91a49c5147db1a9aaf244f05a434d6486931d2d14271b9e35030b71fd73da179069b32e2935630e1c2062354d0da20a6c416e50be794ca4", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     BigInteger bigInteger4 = new BigInteger("e9e642599d355f37c97ffd3567120b8e25c9cd43e927b3a9670fbec5d890141922d2c3b3ad2480093799869d1e846aab49fab0ad26d2ce6a22219d470bce7d777d4a21fbe9c270b57f607002f3cef8393694cf45ee3688c11a8c56ab127a3daf", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     BigInteger bigInteger5 = new BigInteger("9cdbd84c9f1ac2f38d0f80f42ab952e7338bf511", 16);
/*     */ 
/*     */ 
/*     */     
/* 214 */     BigInteger bigInteger6 = new BigInteger("30470ad5a005fb14ce2d9dcd87e38bc7d1b1c5facbaecbe95f190aa7a31d23c4dbbcbe06174544401a5b2c020965d8c2bd2171d3668445771f74ba084d2029d83c1c158547f3a9f1a2715be23d51ae4d3e5a1f6a7064f316933a346d3f529252", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     BigInteger bigInteger7 = new BigInteger("fd7f53811d75122952df4a9c2eece4e7f611b7523cef4400c31e3f80b6512669455d402251fb593d8d58fabfc5f5ba30f6cb9b556cd7813b801d346ff26660b76b9950a5a49f9fe8047b1022c24fbba9d7feb7c61bf83b57e7c6a8a6150f04fb83f6d3c51ec3023554135a169132f675f3ae2b61d72aeff22203199dd14801c7", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     BigInteger bigInteger8 = new BigInteger("9760508f15230bccb292b982a2eb840bf0581cf5", 16);
/*     */ 
/*     */ 
/*     */     
/* 240 */     BigInteger bigInteger9 = new BigInteger("f7e1a085d69b3ddecbbcab5c36b857b97994afbbfa3aea82f9574c0b3d0782675159578ebad4594fe67107108180b449167123e84c281613b7cf09328cc8a6e13c167a8b547c8d28e0a3ae1e2bb3a675916ea37f0bfa213562f1fb627a01243bcca4f1bea8519089a883dfe15ae59f06928b665e807b552564014c3bfecf492a", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     dsaCache.put(Integer.valueOf(672), new DSAParameterSpec(bigInteger1, bigInteger2, bigInteger3));
/*     */     
/* 251 */     dsaCache.put(Integer.valueOf(928), new DSAParameterSpec(bigInteger4, bigInteger5, bigInteger6));
/*     */     
/* 253 */     dsaCache.put(Integer.valueOf(1184), new DSAParameterSpec(bigInteger7, bigInteger8, bigInteger9));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     BigInteger bigInteger10 = new BigInteger("8f7935d9b9aae9bfabed887acf4951b6f32ec59e3baf3718e8eac4961f3efd3606e74351a9c4183339b809e7c2ae1c539ba7475b85d011adb8b47987754984695cac0e8f14b3360828a22ffa27110a3d62a993453409a0fe696c4658f84bdd20819c3709a01057b195adcd00233dba5484b6291f9d648ef883448677979cec04b434a6ac2e75e9985de23db0292fc1118c9ffa9d8181e7338db792b730d7b9e349592f68099872153915ea3d6b8b4653c633458f803b32a4c2e0f27290256e4e3f8a3b0838a1c450e4e18c1a29a37ddf5ea143de4b66ff04903ed5cf1623e158d487c608e97f211cd81dca23cb6e380765f822e342be484c05763939601cd667", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     BigInteger bigInteger11 = new BigInteger("baf696a68578f7dfdee7fa67c977c785ef32b233bae580c0bcd5695d", 16);
/*     */ 
/*     */ 
/*     */     
/* 279 */     BigInteger bigInteger12 = new BigInteger("16a65c58204850704e7502a39757040d34da3a3478c154d4e4a5c02d242ee04f96e61e4bd0904abdac8f37eeb1e09f3182d23c9043cb642f88004160edf9ca09b32076a79c32a627f2473e91879ba2c4e744bd2081544cb55b802c368d1fa83ed489e94e0fa0688e32428a5c78c478c68d0527b71c9a3abb0b0be12c44689639e7d3ce74db101a65aa2b87f64c6826db3ec72f4b5599834bb4edb02f7c90e9a496d3a55d535bebfc45d4f619f63f3dedbb873925c2f224e07731296da887ec1e4748f87efb5fdeb75484316b2232dee553ddaf02112b0d1f02da30973224fe27aeda8b9d4b2922d9ba8be39ed9e103a63c52810bc688b7e2ed4316e1ef17dbde", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     dsaCache.put(Integer.valueOf(2272), new DSAParameterSpec(bigInteger10, bigInteger11, bigInteger12));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     BigInteger bigInteger13 = new BigInteger("95475cf5d93e596c3fcd1d902add02f427f5f3c7210313bb45fb4d5bb2e5fe1cbd678cd4bbdd84c9836be1f31c0777725aeb6c2fc38b85f48076fa76bcd8146cc89a6fb2f706dd719898c2083dc8d896f84062e2c9c94d137b054a8d8096adb8d51952398eeca852a0af12df83e475aa65d4ec0c38a9560d5661186ff98b9fc9eb60eee8b030376b236bc73be3acdbd74fd61c1d2475fa3077b8f080467881ff7e1ca56fee066d79506ade51edbb5443a563927dbc4ba520086746175c8885925ebc64c6147906773496990cb714ec667304e261faee33b3cbdf008e0c3fa90650d97d3909c9275bf4ac86ffcb3d03e6dfc8ada5934242dd6d3bcca2a406cb0b", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     BigInteger bigInteger14 = new BigInteger("f8183668ba5fc5bb06b5981e6d8b795d30b8978d43ca0ec572e37e09939a9773", 16);
/*     */ 
/*     */ 
/*     */     
/* 322 */     BigInteger bigInteger15 = new BigInteger("42debb9da5b3d88cc956e08787ec3f3a09bba5f48b889a74aaf53174aa0fbe7e3c5b8fcd7a53bef563b0e98560328960a9517f4014d3325fc7962bf1e049370d76d1314a76137e792f3f0db859d095e4a5b932024f079ecf2ef09c797452b0770e1350782ed57ddf794979dcef23cb96f183061965c4ebc93c9c71c56b925955a75f94cccf1449ac43d586d0beee43251b0b2287349d68de0d144403f13e802f4146d882e057af19b6f6275c6676c8fa0e3ca2713a3257fd1b27d0639f695e347d8d1cf9ac819a26ca9b04cb0eb9b7b035988d15bbac65212a55239cfc7e58fae38d7250ab9991ffbc97134025fe8ce04c4399ad96569be91a546f4978693c7a", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     dsaCache.put(Integer.valueOf(2304), new DSAParameterSpec(bigInteger13, bigInteger14, bigInteger15));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     BigInteger bigInteger16 = new BigInteger("ea9cda9f5fbda66dd830494609405687ab7cf38538e058d1e2f68dea95364866e1c05beacded24227edee28cad80bcecad39913be3b713267b3b96c8d9f0f6a03b5dfc9222d5cfe4afcc9982f33784f760c3b759aebe3bbe9098a6b84c96f1fde44ce11c084c2a082c7a76a0ef142928b4f328406ab9beb24f84577dd0f46ce86fd8f08488269998bf4742d6425f7a0ec75d8660c5dd6f4e3b3d3bee81b2c21afe8c9e8b84b87192e2cc20f961d2bcd8133afcf3675ab80681cb374c78f33e29d1011083d89f9c5728b94676fccb1b57bc60288c15d85ae838ae1941c5a20ae2b2049b3583fe30da455ddb3e6ad9b9955cd9bb5681431622beb0f92da533fcab496cebc447aa1bb5a8039522f2da98ff416289323a64df626ab6881870927dcee387f13b5c9d24d6cba1d82ed375a082506ee87bc7ae30067f4a94e2ee363d992c40f2725b5db4b3525ebde22bbbfd0fa124a588b0f5a4acb3a86951aff09f8c8198fb5b53da0c931cedc598b4f835b779d04d99026c7ba08c4b27f118ac1e3d", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     BigInteger bigInteger17 = new BigInteger("c4eeac2bbab79bd831946d717a56a6e687547aa8e9c5494a5a4b2f4ca13d6c11", 16);
/*     */ 
/*     */ 
/*     */     
/* 369 */     BigInteger bigInteger18 = new BigInteger("42e5fa7844f8fa9d8998d830d004e7b15b1d276bcbe5f12c35ec90c1a25f5832018a6724bd9cdbe803b675509bed167f3d7cf8599fc865c6d5a0f79158c1bc918f00a944d0ad0f38f520fb91d85d82674d0d5f874faa5fcdfe56cd178c1afdc7ce8795727b7dee966ed0b3c5cedcef8aca628befebf2d105c7aff8eb0da9c9610737dd64dce1237b82c1b2bc8608d55ffda98d7189444e65883315669c05716bde36c78b130aa3df2e4d609914c7c8dc470f4e300187c775f81e7b1a9c0dce405d6eab2cbb9d9c4ef44412ba573dd403c4ed7bc2364772f56a30c48de78f5003f9371c55262d2c8ac2246ade3b02fdcfcf5cbfde74fbcbfe6e0e0fdf3160764f84d311c179a40af679a8f47ab13c8f706893245eb11edcce451fa2ab980019987f125d8dc96622d419ba0d71f16c6024dce9d364c3b26d8ec1a3c828f6c9d14b1d0333b95db77bfdbe3c6bce5337a1a5a7ace10111219448447197e2a344cc423be768bb89e27be6cbd22085614a5a3360be23b1bfbb6e6e6471363d32c85d31", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 387 */     dsaCache.put(Integer.valueOf(3328), new DSAParameterSpec(bigInteger16, bigInteger17, bigInteger18));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     BigInteger bigInteger19 = BigInteger.valueOf(2L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     BigInteger bigInteger20 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A63A3620FFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     BigInteger bigInteger21 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 420 */     BigInteger bigInteger22 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA237327FFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 431 */     BigInteger bigInteger23 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AACAA68FFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     BigInteger bigInteger24 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A93AD2CAFFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 464 */     BigInteger bigInteger25 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A92108011A723C12A787E6D788719A10BDBA5B2699C327186AF4E23C1A946834B6150BDA2583E9CA2AD44CE8DBBBC2DB04DE8EF92E8EFC141FBECAA6287C59474E6BC05D99B2964FA090C3A2233BA186515BE7ED1F612970CEE2D7AFB81BDD762170481CD0069127D5B05AA993B4EA988D8FDDC186FFB7DC90A6C08F4DF435C934063199FFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     BigInteger bigInteger26 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A92108011A723C12A787E6D788719A10BDBA5B2699C327186AF4E23C1A946834B6150BDA2583E9CA2AD44CE8DBBBC2DB04DE8EF92E8EFC141FBECAA6287C59474E6BC05D99B2964FA090C3A2233BA186515BE7ED1F612970CEE2D7AFB81BDD762170481CD0069127D5B05AA993B4EA988D8FDDC186FFB7DC90A6C08F4DF435C93402849236C3FAB4D27C7026C1D4DCB2602646DEC9751E763DBA37BDF8FF9406AD9E530EE5DB382F413001AEB06A53ED9027D831179727B0865A8918DA3EDBEBCF9B14ED44CE6CBACED4BB1BDB7F1447E6CC254B332051512BD7AF426FB8F401378CD2BF5983CA01C64B92ECF032EA15D1721D03F482D7CE6E74FEF6D55E702F46980C82B5A84031900B1C9E59E7C97FBEC7E8F323A97A7E36CC88BE0F1D45B7FF585AC54BD407B22B4154AACC8F6D7EBF48E1D814CC5ED20F8037E0A79715EEF29BE32806A1D58BB7C5DA76F550AA3D8A1FBFF0EB19CCB1A313D55CDA56C9EC2EF29632387FE8D76E3C0468043E8F663F4860EE12BF2D5B0B7474D6E694F91E6DCC4024FFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 520 */     BigInteger bigInteger27 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A92108011A723C12A787E6D788719A10BDBA5B2699C327186AF4E23C1A946834B6150BDA2583E9CA2AD44CE8DBBBC2DB04DE8EF92E8EFC141FBECAA6287C59474E6BC05D99B2964FA090C3A2233BA186515BE7ED1F612970CEE2D7AFB81BDD762170481CD0069127D5B05AA993B4EA988D8FDDC186FFB7DC90A6C08F4DF435C93402849236C3FAB4D27C7026C1D4DCB2602646DEC9751E763DBA37BDF8FF9406AD9E530EE5DB382F413001AEB06A53ED9027D831179727B0865A8918DA3EDBEBCF9B14ED44CE6CBACED4BB1BDB7F1447E6CC254B332051512BD7AF426FB8F401378CD2BF5983CA01C64B92ECF032EA15D1721D03F482D7CE6E74FEF6D55E702F46980C82B5A84031900B1C9E59E7C97FBEC7E8F323A97A7E36CC88BE0F1D45B7FF585AC54BD407B22B4154AACC8F6D7EBF48E1D814CC5ED20F8037E0A79715EEF29BE32806A1D58BB7C5DA76F550AA3D8A1FBFF0EB19CCB1A313D55CDA56C9EC2EF29632387FE8D76E3C0468043E8F663F4860EE12BF2D5B0B7474D6E694F91E6DBE115974A3926F12FEE5E438777CB6A932DF8CD8BEC4D073B931BA3BC832B68D9DD300741FA7BF8AFC47ED2576F6936BA424663AAB639C5AE4F5683423B4742BF1C978238F16CBE39D652DE3FDB8BEFC848AD922222E04A4037C0713EB57A81A23F0C73473FC646CEA306B4BCBC8862F8385DDFA9D4B7FA2C087E879683303ED5BDD3A062B3CF5B3A278A66D2A13F83F44F82DDF310EE074AB6A364597E899A0255DC164F31CC50846851DF9AB48195DED7EA1B1D510BD7EE74D73FAF36BC31ECFA268359046F4EB879F924009438B481C6CD7889A002ED5EE382BC9190DA6FC026E479558E4475677E9AA9E3050E2765694DFC81F56E880B96E7160C980DD98EDD3DFFFFFFFFFFFFFFFFF", 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 566 */     dhCache.put(Integer.valueOf(512), new DHParameterSpec(bigInteger1, bigInteger3));
/*     */     
/* 568 */     dhCache.put(Integer.valueOf(768), new DHParameterSpec(bigInteger20, bigInteger19));
/* 569 */     dhCache.put(Integer.valueOf(1024), new DHParameterSpec(bigInteger21, bigInteger19));
/* 570 */     dhCache.put(Integer.valueOf(1536), new DHParameterSpec(bigInteger22, bigInteger19));
/* 571 */     dhCache.put(Integer.valueOf(2048), new DHParameterSpec(bigInteger23, bigInteger19));
/* 572 */     dhCache.put(Integer.valueOf(3072), new DHParameterSpec(bigInteger24, bigInteger19));
/* 573 */     dhCache.put(Integer.valueOf(4096), new DHParameterSpec(bigInteger25, bigInteger19));
/* 574 */     dhCache.put(Integer.valueOf(6144), new DHParameterSpec(bigInteger26, bigInteger19));
/* 575 */     dhCache.put(Integer.valueOf(8192), new DHParameterSpec(bigInteger27, bigInteger19));
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\ParameterCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */