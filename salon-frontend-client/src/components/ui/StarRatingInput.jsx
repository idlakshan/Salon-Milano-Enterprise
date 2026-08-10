import { useState } from "react";
import { Star } from "lucide-react";

const StarRatingInput = ({ rating, onRatingChange }) => {
  const [hoveredRating, setHoveredRating] = useState(0);

  return (
    <div className="flex items-center gap-1.5">
      {[1, 2, 3, 4, 5].map((starIndex) => {
        const isFilled = starIndex <= (hoveredRating || rating);

        return (
          <button
            key={starIndex}
            type="button"
            onClick={() => onRatingChange(starIndex)}
            onMouseEnter={() => setHoveredRating(starIndex)}
            onMouseLeave={() => setHoveredRating(0)}
            className="p-1 cursor-pointer transition-transform hover:scale-110 focus:outline-none"
          >
            <Star
              className={`w-7 h-7 transition-colors ${
                isFilled
                  ? "fill-amber-400 text-amber-400"
                  : "text-brand-silver/40 hover:text-amber-400/50"
              }`}
            />
          </button>
        );
      })}
    </div>
  );
};

export default StarRatingInput;
