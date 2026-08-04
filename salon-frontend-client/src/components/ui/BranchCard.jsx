import { MapPin, Phone, Clock, Star } from "lucide-react";

const BranchCard = ({ branch }) => {
  const { city, address, phone, hours, rating, reviewsCount, image } = branch;

  return (
    <div className="rounded-2xl bg-brand-dark-paper border border-brand-dark-border overflow-hidden hover:border-brand-silver-border transition-all duration-300 flex flex-col group h-full">
      <div className="h-48 w-full overflow-hidden relative">
        <img
          src={image}
          alt={city}
          loading="lazy"
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-700"
        />
        <div className="absolute inset-0 bg-linear-to-t from-brand-dark-paper via-transparent to-transparent" />

        <div
          className="absolute top-3 right-3 flex items-center gap-1.5 px-2.5 py-1 rounded-full
         bg-black/70 backdrop-blur-md border border-white/10 text-xs font-semibold text-white"
        >
          <Star className="w-3.5 h-3.5 fill-amber-400 text-amber-400" />
          <span>{rating}</span>
          <span className="text-[10px] text-brand-silver font-normal">
            ({reviewsCount})
          </span>
        </div>
      </div>

      <div className="p-6 flex-1 flex flex-col justify-between">
        <div>
          <h4 className="text-xl font-bold text-white mb-4 line-clamp-1">
            {city}
          </h4>

          <div className="space-y-3 text-xs sm:text-sm text-brand-silver">
            <div className="flex items-start gap-3">
              <MapPin className="w-4 h-4 text-brand-red-light shrink-0 mt-0.5" />
              <span>{address}</span>
            </div>
            <div className="flex items-center gap-3">
              <Phone className="w-4 h-4 text-brand-red-light shrink-0" />
              <span>{phone}</span>
            </div>
            <div className="flex items-center gap-3">
              <Clock className="w-4 h-4 text-brand-red-light shrink-0" />
              <span>{hours}</span>
            </div>
          </div>
        </div>

        <button
          className="mt-6 w-full py-2.5 rounded-xl border border-brand-dark-border hover:border-brand-red
         hover:bg-brand-red text-white text-xs font-semibold transition-all cursor-pointer"
        >
          Book at {city.split(" ")[0]}
        </button>
      </div>
    </div>
  );
};

export default BranchCard;
