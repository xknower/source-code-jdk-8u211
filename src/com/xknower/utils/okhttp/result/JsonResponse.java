package com.xknower.utils.okhttp.result;

/**
 * 基本JSON 结构数据响应实体
 *
 * @author xknower
 */
public class JsonResponse<T> {

    /**
     * 返回结果, 编码
     */
    private String result;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回错误时, 错误信息
     */
    private String error;

    private JsonResponse() {
    }

    private JsonResponse(String result, T data, String error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static JsonResponse success() {
        return success("");
    }

    public static <T> JsonResponse<T> success(Object object) {
        // 返回成功
        return new JsonResponse("SN_000000", object, null);
    }

    public static JsonResponse fail(String errorCode, String message) {
        // 返回错误
        return new JsonResponse(errorCode, null, message);
    }

    public static JsonResponse fail() {
        return fail("SN_000001", "系统错误");
    }

    public static JsonResponse fail(String message) {
        return fail("SN_000001", message);
    }
}
