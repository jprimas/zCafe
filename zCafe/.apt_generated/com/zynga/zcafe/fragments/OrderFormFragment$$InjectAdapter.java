// Code generated by dagger-compiler.  Do not edit.
package com.zynga.zcafe.fragments;


import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

/**
 * A {@code Binder<OrderFormFragment>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code OrderFormFragment} and its
 * dependencies.
 *
 * Being a {@code Provider<OrderFormFragment>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<OrderFormFragment>} and handling injection
 * of annotated fields.
 */
public final class OrderFormFragment$$InjectAdapter extends Binding<OrderFormFragment>
    implements Provider<OrderFormFragment>, MembersInjector<OrderFormFragment> {
  private Binding<com.zynga.zcafe.CafeApplication> app;
  private Binding<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus> bus;
  private Binding<android.view.inputmethod.InputMethodManager> imm;
  private Binding<com.zynga.zcafe.services.CafeService> service;

  public OrderFormFragment$$InjectAdapter() {
    super("com.zynga.zcafe.fragments.OrderFormFragment", "members/com.zynga.zcafe.fragments.OrderFormFragment", NOT_SINGLETON, OrderFormFragment.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    app = (Binding<com.zynga.zcafe.CafeApplication>) linker.requestBinding("com.zynga.zcafe.CafeApplication", OrderFormFragment.class, getClass().getClassLoader());
    bus = (Binding<com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus>) linker.requestBinding("com.zynga.zcafe.inject.modules.CafeModule$MainThreadBus", OrderFormFragment.class, getClass().getClassLoader());
    imm = (Binding<android.view.inputmethod.InputMethodManager>) linker.requestBinding("android.view.inputmethod.InputMethodManager", OrderFormFragment.class, getClass().getClassLoader());
    service = (Binding<com.zynga.zcafe.services.CafeService>) linker.requestBinding("com.zynga.zcafe.services.CafeService", OrderFormFragment.class, getClass().getClassLoader());
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(app);
    injectMembersBindings.add(bus);
    injectMembersBindings.add(imm);
    injectMembersBindings.add(service);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<OrderFormFragment>}.
   */
  @Override
  public OrderFormFragment get() {
    OrderFormFragment result = new OrderFormFragment();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<OrderFormFragment>}.
   */
  @Override
  public void injectMembers(OrderFormFragment object) {
    object.app = app.get();
    object.bus = bus.get();
    object.imm = imm.get();
    object.service = service.get();
  }
}
