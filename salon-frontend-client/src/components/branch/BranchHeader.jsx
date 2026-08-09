import { MapPin, Clock, Star } from "lucide-react";

const BranchHeader = ({ branch }) => {
  const { name, city, openTime, closeTime, rating, images } = branch;

  return (
    <div className="space-y-4">
      <div className="space-y-2">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3">
          <h1 className="text-2xl sm:text-3xl font-extrabold text-white tracking-tight">
            {name}
          </h1>

          <div className="self-start sm:self-auto flex items-center gap-1.5 bg-brand-dark-paper border border-brand-dark-border px-3 py-1.5 rounded-xl text-xs font-bold text-white shrink-0">
            <Star className="w-3.5 h-3.5 fill-amber-400 text-amber-400" />
            <span>{rating}</span>
          </div>
        </div>

        <div className="flex flex-wrap items-center gap-4 text-xs sm:text-sm text-brand-silver">
          <span className="flex items-center gap-1.5">
            <MapPin className="w-4 h-4 text-brand-red-light shrink-0" />
            {city}
          </span>
          <span className="text-brand-dark-border">•</span>
          <span className="flex items-center gap-1.5">
            <Clock className="w-4 h-4 text-brand-red-light shrink-0" />
            {openTime} - {closeTime}
          </span>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 h-70 sm:h-90 rounded-2xl overflow-hidden border border-brand-dark-border/50">
        <div className="md:col-span-2 h-full overflow-hidden">
          <img
            src={images[0]}
            alt={name}
            className="w-full h-full object-cover hover:scale-105 transition-transform duration-500"
          />
        </div>
        <div className="hidden md:grid grid-rows-2 gap-4 h-full overflow-hidden">
          <img
            src={images[1]}
            alt={`${name} interior 1`}
            className="w-full h-full object-cover hover:scale-105 transition-transform duration-500"
          />
          <img
            src={images[2]}
            alt={`${name} interior 2`}
            className="w-full h-full object-cover hover:scale-105 transition-transform duration-500"
          />
        </div>
      </div>
    </div>
  );
};

export default BranchHeader;
