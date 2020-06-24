package com.xknower.utils.okhttp.client;

import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 具有鉴权的 HTTP 请求对象
 *
 * @author xknower
 */
abstract class HttpClientCookieJar implements CookieJar {

    /**
     * 是否登录 (需要登录鉴权时, 登录鉴权后变为 True)
     */
    private boolean login = false;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    /**
     * Cookie 全部放在顶级域名
     */
    private String getHost(HttpUrl httpUrl) {
        String[] ss = httpUrl.uri().getHost().split(".");
        ss = subarray(ss, ss.length - 2, ss.length);
        return join(ss, ".");
    }

    /**
     * 执行登录, 登录后 login 设置未 True
     */
    protected void doLogin() {
        setLogin(true);
    }

    /**
     * 构造初始化 OkHttpClient
     *
     * @return OkHttpClient
     */
    protected OkHttpClient initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent =
                                originalRequest.newBuilder()
                                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36")
                                        .build();
                        return chain.proceed(requestWithUserAgent);
                    }
                })
                .cookieJar(this)
                .build();
        return okHttpClient;
    }

    /**
     * 清除缓存 Cookie
     */
    public void clear() {
        cookieStore.clear();
    }

    /**
     * 保存 Cookie
     */
    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        String host = getHost(httpUrl);
        List<Cookie> r = new ArrayList<>();
        List<Cookie> cookies = cookieStore.get(host);
        if (cookies == null) {
            cookies = new ArrayList<>();
        }
        r.addAll(list);
        for (Cookie c : cookies) {
            boolean hasSame = false;
            for (Cookie o : r) {
                if (c.domain().equals(o.domain()) && c.name().equals(o.name()) && c.path().equals(o.path())) {
                    hasSame = true;
                    break;
                }
            }
            if (!hasSame) {
                r.add(c);
            }
        }
        cookies.addAll(list);
        //
        cookieStore.put(host, r);
    }

    /**
     * 加载 Cookie
     */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(getHost(url));
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }


    private static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    private static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    private static String[] subarray(String[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            Class<?> type = array.getClass().getComponentType();
            String[] subarray;
            if (newSize <= 0) {
                subarray = (String[]) Array.newInstance(type, 0);
                return subarray;
            } else {
                subarray = (String[]) Array.newInstance(type, newSize);
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }
}
