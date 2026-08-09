import { Plus, Check, Clock } from "lucide-react";

const ServiceCard = ({ service, isAdded, onAddToCart }) => {
  return (
    <div className="p-4 rounded-2xl bg-brand-dark-paper border border-brand-dark-border flex items-center justify-between gap-4 hover:border-brand-silver-border transition-all">
      <div className="flex items-center gap-4">
        <img
          src={service.image}
          alt={service.name}
          className="w-16 h-16 rounded-xl object-cover shrink-0"
        />

        <div className="space-y-1">
          <h4 className="text-sm font-bold text-white">{service.name}</h4>
          <div className="flex items-center gap-2 text-xs text-brand-silver">
            <span className="font-semibold text-brand-red-light">
              LKR {service.price.toLocaleString()}
            </span>
            <span>•</span>
            <span className="flex items-center gap-1">
              <Clock className="w-3 h-3 text-brand-silver" />
              {service.duration}
            </span>
          </div>
        </div>
      </div>

      <button
        onClick={() => onAddToCart(service)}
        disabled={isAdded}
        className={`px-4 py-2 rounded-xl text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer shrink-0 ${
          isAdded
            ? "bg-emerald-600/20 text-emerald-400 border border-emerald-500/30 cursor-default"
            : "bg-brand-red hover:bg-brand-red-hover text-white active:scale-95"
        }`}
      >
        {isAdded ? (
          <>
            <Check className="w-3.5 h-3.5" /> Added
          </>
        ) : (
          <>
            <Plus className="w-3.5 h-3.5" /> Add
          </>
        )}
      </button>
    </div>
  );
};

export default ServiceCard;
