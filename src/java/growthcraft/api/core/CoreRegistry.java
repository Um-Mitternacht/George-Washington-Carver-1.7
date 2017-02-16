package growthcraft.api.core;

import growthcraft.api.core.fluids.FluidDictionary;
import growthcraft.api.core.fluids.FluidTagsRegistry;
import growthcraft.api.core.fluids.IFluidDictionary;
import growthcraft.api.core.fluids.IFluidTagsRegistry;
import growthcraft.api.core.log.ILoggable;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.log.NullLogger;
import growthcraft.api.core.vines.IVineDropRegistry;
import growthcraft.api.core.vines.VineDropRegistry;

import javax.annotation.Nonnull;

public class CoreRegistry implements ILoggable {
    private static final CoreRegistry instance = new CoreRegistry();
    private final IFluidDictionary fluidDictionary = new FluidDictionary();
    private final IFluidTagsRegistry fluidTagsRegistry = new FluidTagsRegistry();
    private final IEffectRegistry effectRegistry = new EffectRegistry().initialize();
    private final IPotionEffectFactoryRegistry potionEffectFactoryRegistry = new PotionEffectFactoryRegistry();
    private final IVineDropRegistry vineDropRegistry = new VineDropRegistry();
    protected ILogger logger = NullLogger.INSTANCE;

    public static final CoreRegistry instance() {
        return instance;
    }

    public ILogger getLogger() {
        return logger;
    }

    @Override
    public void setLogger(@Nonnull ILogger l) {
        this.logger = l;
        fluidTagsRegistry.setLogger(logger);
        fluidDictionary.setLogger(logger);
        effectRegistry.setLogger(logger);
        potionEffectFactoryRegistry.setLogger(logger);
        vineDropRegistry.setLogger(logger);
    }

    public IEffectRegistry getEffectsRegistry() {
        return effectRegistry;
    }

    public IPotionEffectFactoryRegistry getPotionEffectFactoryRegistry() {
        return potionEffectFactoryRegistry;
    }

    /**
     * @return instance of the FluidTagsRegistry
     */
    public IFluidTagsRegistry fluidTags() {
        return fluidTagsRegistry;
    }

    public IFluidDictionary fluidDictionary() {
        return fluidDictionary;
    }

    public IVineDropRegistry vineDrops() {
        return vineDropRegistry;
    }
}
