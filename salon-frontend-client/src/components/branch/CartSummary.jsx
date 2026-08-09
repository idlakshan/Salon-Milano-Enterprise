import { ShoppingBag } from "lucide-react";
import CartItem from "../ui/CartItem";

const CartSummary = ({ cartItems, onRemoveFromCart, totalPrice }) => {
  return (
    <div className="lg:col-span-4 bg-brand-dark-paper p-5 rounded-2xl border border-brand-dark-border space-y-4 sticky top-24">
      <div className="flex items-center justify-between border-b border-brand-dark-border pb-3">
        <div className="flex items-center gap-2">
          <ShoppingBag className="w-4 h-4 text-brand-red-light" />
          <h3 className="text-sm font-bold text-white">Your Cart</h3>
        </div>
        <span className="text-xs bg-brand-dark-bg px-2.5 py-1 rounded-full border border-brand-dark-border text-brand-silver font-semibold">
          {cartItems.length} items
        </span>
      </div>

      {cartItems.length === 0 ? (
        <p className="text-xs text-brand-silver py-6 text-center">
          No services added to cart yet.
        </p>
      ) : (
        <div className="space-y-3 max-h-60 overflow-y-auto pr-1">
          {cartItems.map((item) => (
            <CartItem key={item.id} item={item} onRemove={onRemoveFromCart} />
          ))}
        </div>
      )}

      <div className="border-t border-brand-dark-border pt-4 space-y-3">
        <div className="flex items-center justify-between text-sm font-bold">
          <span className="text-brand-silver">Total Amount:</span>
          <span className="text-brand-red-light text-base">
            LKR {totalPrice.toLocaleString()}
          </span>
        </div>

        <button
          disabled={cartItems.length === 0}
          className="w-full py-3 rounded-xl bg-brand-red hover:bg-brand-red-hover text-white text-xs font-bold transition-all disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer active:scale-95"
        >
          Proceed to Booking
        </button>
      </div>
    </div>
  );
};

export default CartSummary;
