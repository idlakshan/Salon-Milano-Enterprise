import { MapPin, Clock, Star } from "lucide-react";

const BranchHeader = ({ branch }) => {
  const { name, city, openTime, closeTime, rating, totalReviews, images } =
    branch;

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 h-70 sm:h-90 rounded-2xl overflow-hidden">
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

      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 p-6 rounded-2xl bg-brand-dark-paper border border-brand-dark-border">
        <div className="space-y-2">
          <h1 className="text-2xl sm:text-3xl font-extrabold text-white">
            {name}
          </h1>

          <div className="flex flex-wrap items-center gap-4 text-xs sm:text-sm text-brand-silver">
            <span className="flex items-center gap-1.5">
              <MapPin className="w-4 h-4 text-brand-red-light shrink-0" />
              {city}
            </span>
            <span className="flex items-center gap-1.5">
              <Clock className="w-4 h-4 text-brand-red-light shrink-0" />
              {openTime} - {closeTime}
            </span>
          </div>
        </div>

        <div className="self-start sm:self-center flex items-center gap-1.5 bg-brand-dark-bg border border-brand-dark-border px-3.5 py-2 rounded-xl text-xs font-bold text-white">
          <Star className="w-4 h-4 fill-amber-400 text-amber-400" />
          <span>{rating}</span>
          <span className="text-[11px] text-brand-silver font-normal">
            ({totalReviews})
          </span>
        </div>
      </div>
    </div>
  );
};

export default BranchHeader;
