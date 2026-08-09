import { Trash2 } from "lucide-react";

const CartItem = ({ item, onRemove }) => {
  return (
    <div className="flex items-center justify-between text-xs bg-brand-dark-bg p-3 rounded-xl border border-brand-dark-border">
      <div>
        <p className="font-semibold text-white">{item.name}</p>
        <p className="text-brand-silver text-[11px]">
          LKR {item.price.toLocaleString()}
        </p>
      </div>
      <button
        onClick={() => onRemove(item.id)}
        className="text-brand-silver hover:text-brand-red-light transition-colors p-1 cursor-pointer"
      >
        <Trash2 className="w-4 h-4" />
      </button>
    </div>
  );
};

export default CartItem;
