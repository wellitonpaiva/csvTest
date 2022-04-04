package com.thescientist.csvTest

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.FileReader

internal class CsvTest {

    val csvMapper = CsvMapper().apply {
        registerModule(KotlinModule())
    }

    inline fun <reified T> readCsvFile(fileName: String): Collection<T> {
        FileReader(fileName).use { reader ->
            return csvMapper
                .readerFor(T::class.java)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues<T>(reader)
                .readAll()
        }
    }

    @Test
    fun readCsv() {
        assertThat(readCsvFile<ModelCsv>("test.csv")).isEqualTo( listOf(
            ModelCsv(1, "test1", "23.12.2012"),
            ModelCsv(2, "test2", "24.12.2013"),
            ModelCsv(3, "test3", "25.12.2014")
        ))
    }
}