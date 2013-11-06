// Code generated by dagger-compiler.  Do not edit.
package com.zynga.zcafe.fragments;


import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

/**
 * A {@code Binder<FavoriteListFragment>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code FavoriteListFragment} and its
 * dependencies.
 *
 * Being a {@code Provider<FavoriteListFragment>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<FavoriteListFragment>} and handling injection
 * of annotated fields.
 */
public final class FavoriteListFragment$$InjectAdapter extends Binding<FavoriteListFragment>
    implements Provider<FavoriteListFragment>, MembersInjector<FavoriteListFragment> {
  private Binding<com.zynga.zcafe.CafeApplication> app;
  private Binding<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus> bus;
  private Binding<com.zynga.zcafe.services.CafeService> service;
  private Binding<BaseListFragment> supertype;

  public FavoriteListFragment$$InjectAdapter() {
    super("com.zynga.zcafe.fragments.FavoriteListFragment", "members/com.zynga.zcafe.fragments.FavoriteListFragment", NOT_SINGLETON, FavoriteListFragment.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    app = (Binding<com.zynga.zcafe.CafeApplication>) linker.requestBinding("com.zynga.zcafe.CafeApplication", FavoriteListFragment.class, getClass().getClassLoader());
    bus = (Binding<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus>) linker.requestBinding("com.zynga.zcafe.inject.modules.CafeModule$MainThreadBus", FavoriteListFragment.class, getClass().getClassLoader());
    service = (Binding<com.zynga.zcafe.services.CafeService>) linker.requestBinding("com.zynga.zcafe.services.CafeService", FavoriteListFragment.class, getClass().getClassLoader());
    supertype = (Binding<BaseListFragment>) linker.requestBinding("members/com.zynga.zcafe.fragments.BaseListFragment", FavoriteListFragment.class, getClass().getClassLoader(), false, true);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(app);
    injectMembersBindings.add(bus);
    injectMembersBindings.add(service);
    injectMembersBindings.add(supertype);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<FavoriteListFragment>}.
   */
  @Override
  public FavoriteListFragment get() {
    FavoriteListFragment result = new FavoriteListFragment();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<FavoriteListFragment>}.
   */
  @Override
  public void injectMembers(FavoriteListFragment object) {
    object.app = app.get();
    object.bus = bus.get();
    object.service = service.get();
    supertype.injectMembers(object);
  }
}