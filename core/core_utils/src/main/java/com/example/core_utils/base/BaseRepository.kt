package com.example.core_utils.base


import android.util.Log
import com.example.core_utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import org.json.JSONObject


abstract class BaseRepository {

    protected fun <T> doRequest(
        request: suspend () -> T
    ) = flow<Resource<T>> {
        emit(Resource.Success(request()))
    }.flowOn(Dispatchers.IO).catch { e ->
        val errorMessage = when {
            e is retrofit2.HttpException -> {
                when (e.code()) {
                    in 400..499 -> {
                        val errorBody = e.response()?.errorBody()?.string()
                        try {
                            val errorJson = JSONObject(errorBody ?: "")
                            val errorKeys = errorJson.keys()
                            val firstKey = if (errorKeys.hasNext()) errorKeys.next() else null
                            errorJson.optString(firstKey, "Неизвестная ошибка")
                        } catch (jsonException: JSONException) {
                            "Ошибка сервера: ${e.code()}"
                        }
                    }
                    else -> "Произошла ошибка: ${e.code()}"
                }
            }
            else -> {
                "Неизвестная ошибка: ${e.message}"
                Log.e("BaseRepository", "Неизвестная ошибка: ${e.message}", e)
            }
        }
        Log.e("BaseRepository", "Ошибка в doRequest: $errorMessage", e)
        emit(Resource.Error(errorMessage.toString()))
    }

    protected fun <T> listRequest(
        request: suspend () -> List<T>
    ) = flow<Resource<List<T>>> {
        emit(Resource.Success(request()))
    }.flowOn(Dispatchers.IO).catch { e ->
        val errorMessage = when {
            e is retrofit2.HttpException -> {
                when (e.code()) {
                    403 -> "Ошибка доступа: У вас нет разрешения на выполнение этого действия"
                    401 -> "Вы не авторизованы"
                    404 -> "Не найдено: Запрошенный ресурс не найден"
                    500 -> "Внутренняя ошибка сервера: Попробуйте позже"
                    503 -> "Сервис не доступен извините"
                    410 -> "Вы удалили аккаунт"
                    else -> "Произошла ошибка: ${e.code()}"
                }
            }

            else -> "Неизвестная ошибка"
        }
        Log.e("baseRep", "Error in listRequest: $errorMessage", e)
        emit(Resource.Error(errorMessage))
    }
}
