import { Star } from "lucide-react";

const ReviewCard = ({ review }) => {
  const { userName, userAvatar, rating, date, comment } = review;

  return (
    <div className="p-5 rounded-2xl bg-brand-dark-paper border border-brand-dark-border space-y-3">
      <div className="flex items-center justify-between gap-4">
        <div className="flex items-center gap-3">
          <img
            src={userAvatar}
            alt={userName}
            className="w-10 h-10 rounded-full object-cover border border-brand-dark-border"
          />
          <div>
            <h4 className="text-sm font-bold text-white">{userName}</h4>
            <p className="text-[11px] text-brand-silver">{date}</p>
          </div>
        </div>

        <div className="flex items-center gap-1 bg-brand-dark-bg border border-brand-dark-border px-2.5 py-1 rounded-lg">
          <Star className="w-3.5 h-3.5 fill-amber-400 text-amber-400" />
          <span className="text-xs font-bold text-white">{rating}.0</span>
        </div>
      </div>

      <p className="text-xs sm:text-sm text-brand-silver leading-relaxed">
        {comment}
      </p>
    </div>
  );
};

export default ReviewCard;
