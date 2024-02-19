package gstv.sicredi.core.di

import gstv.sicredi.core.utils.Mapper
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

inline fun <reified I, reified O> mapperOf() =
    named("Mapper:${I::class.qualifiedName}to${O::class.qualifiedName}")

inline fun <reified I, reified O> Module.mapperFactory(
    noinline definition: Definition<Mapper<I, O>>
) = factory(
    qualifier = mapperOf<I, O>(),
    definition
)

inline fun <reified I, reified O> Scope.getMapperOf(): Mapper<I, O> = get(mapperOf<I, O>())
