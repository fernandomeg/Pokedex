package com.gallardf.pokedex.di.modules

import android.content.Context
import androidx.room.Room
import com.gallardf.pokedex.data.datasource.PokedexDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext context:Context): PokedexDatabase{
        return Room.databaseBuilder(
            context,
            PokedexDatabase::class.java,
            "pokedex_db"
        ).build()
    }

    @Singleton
    @Provides
    fun pokemonDao(pokedexDatabase: PokedexDatabase) =
        pokedexDatabase.pokemonDao()
}