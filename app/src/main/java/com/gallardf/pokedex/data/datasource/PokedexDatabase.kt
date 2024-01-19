package com.gallardf.pokedex.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gallardf.pokedex.data.datasource.dao.PokemonDao
import com.gallardf.pokedex.data.datasource.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokedexDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}