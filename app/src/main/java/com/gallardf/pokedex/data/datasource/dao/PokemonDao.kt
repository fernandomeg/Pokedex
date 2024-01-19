package com.gallardf.pokedex.data.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gallardf.pokedex.data.datasource.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon LIMIT :pageSize OFFSET :offset")
    fun getPokemonList(pageSize: Int, offset: Int): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<PokemonEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pokemon: PokemonEntity)
}