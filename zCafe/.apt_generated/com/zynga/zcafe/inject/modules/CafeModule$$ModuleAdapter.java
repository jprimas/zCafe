// Code generated by dagger-compiler.  Do not edit.
package com.zynga.zcafe.inject.modules;


import dagger.internal.Binding;
import dagger.internal.ModuleAdapter;
import java.util.Map;
import javax.inject.Provider;

/**
 * A manager of modules and provides adapters allowing for proper linking and
 * instance provision of types served by {@code @Provides} methods.
 */
public final class CafeModule$$ModuleAdapter extends ModuleAdapter<CafeModule> {
  private static final String[] INJECTS = { "members/com.zynga.zcafe.services.CafeService", "members/com.zynga.zcafe.CafeApplication", "members/com.zynga.zcafe.activities.CafeActivity", "members/com.zynga.zcafe.activities.RegistrationActivity", "members/com.zynga.zcafe.fragments.BaseListFragment", "members/com.zynga.zcafe.fragments.OrderFormFragment", "members/com.zynga.zcafe.fragments.FavoriteListFragment", "members/com.zynga.zcafe.fragments.MenuListFragment", "members/com.zynga.zcafe.fragments.StatusListFragment", "members/com.zynga.zcafe.events.Producers", "members/com.zynga.zcafe.adapters.MenuAdapter", "members/com.zynga.zcafe.listeners.FragmentTabListener", "members/com.zynga.zcafe.adapters.StatusAdapter", "members/com.zynga.zcafe.fragments.RegistrationFragment", "members/com.zynga.zcafe.inject.modules.CafeModule$MainThreadBus", };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public CafeModule$$ModuleAdapter() {
    super(INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, false /*library*/);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(Map<String, Binding<?>> map) {
    map.put("com.zynga.zcafe.events.Producers", new ProvideProducersProvidesAdapter(module));
    map.put("com.zynga.zcafe.CafeApplication", new ProvideApplicationProvidesAdapter(module));
    map.put("com.zynga.zcafe.inject.modules.CafeModule$MainThreadBus", new ProvideMainThreadBusProvidesAdapter(module));
    map.put("com.zynga.zcafe.services.CafeService", new ProvideCafeServiceProvidesAdapter(module));
  }

  /**
   * A {@code Binder<com.zynga.zcafe.events.Producers>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.zynga.zcafe.events.Producers>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideProducersProvidesAdapter extends Binding<com.zynga.zcafe.events.Producers>
      implements Provider<com.zynga.zcafe.events.Producers> {
    private final CafeModule module;

    public ProvideProducersProvidesAdapter(CafeModule module) {
      super("com.zynga.zcafe.events.Producers", null, IS_SINGLETON, "com.zynga.zcafe.inject.modules.CafeModule.provideProducers()");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.zynga.zcafe.events.Producers>}.
     */
    @Override
    public com.zynga.zcafe.events.Producers get() {
      return module.provideProducers();
    }
  }

  /**
   * A {@code Binder<com.zynga.zcafe.CafeApplication>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.zynga.zcafe.CafeApplication>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideApplicationProvidesAdapter extends Binding<com.zynga.zcafe.CafeApplication>
      implements Provider<com.zynga.zcafe.CafeApplication> {
    private final CafeModule module;

    public ProvideApplicationProvidesAdapter(CafeModule module) {
      super("com.zynga.zcafe.CafeApplication", null, IS_SINGLETON, "com.zynga.zcafe.inject.modules.CafeModule.provideApplication()");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.zynga.zcafe.CafeApplication>}.
     */
    @Override
    public com.zynga.zcafe.CafeApplication get() {
      return module.provideApplication();
    }
  }

  /**
   * A {@code Binder<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideMainThreadBusProvidesAdapter extends Binding<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus>
      implements Provider<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus> {
    private final CafeModule module;

    public ProvideMainThreadBusProvidesAdapter(CafeModule module) {
      super("com.zynga.zcafe.inject.modules.CafeModule$MainThreadBus", null, IS_SINGLETON, "com.zynga.zcafe.inject.modules.CafeModule.provideMainThreadBus()");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus>}.
     */
    @Override
    public com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus get() {
      return module.provideMainThreadBus();
    }
  }

  /**
   * A {@code Binder<com.zynga.zcafe.services.CafeService>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.zynga.zcafe.services.CafeService>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideCafeServiceProvidesAdapter extends Binding<com.zynga.zcafe.services.CafeService>
      implements Provider<com.zynga.zcafe.services.CafeService> {
    private final CafeModule module;

    public ProvideCafeServiceProvidesAdapter(CafeModule module) {
      super("com.zynga.zcafe.services.CafeService", null, IS_SINGLETON, "com.zynga.zcafe.inject.modules.CafeModule.provideCafeService()");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.zynga.zcafe.services.CafeService>}.
     */
    @Override
    public com.zynga.zcafe.services.CafeService get() {
      return module.provideCafeService();
    }
  }
}
