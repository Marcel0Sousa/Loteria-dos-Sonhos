package com.marcelo.loteriadossonhos.data

class BetRepository(private val betDao: BetDao) {

    suspend fun getBets(type: String): List<Bet> {
        // Formas de buscar os dados.
        return betDao.getNumbersByType(type)
    }

    suspend fun insertBet(bet: Bet) {
        return betDao.insert(bet)
    }

    companion object {
        private var instance: BetRepository? = null

        fun getInstance(betDao: BetDao): BetRepository {
            instance = when {

                instance != null -> {
                    instance!!
                }

                else -> {
                    BetRepository(betDao)
                }
            }

            return instance!!
        }
    }
}