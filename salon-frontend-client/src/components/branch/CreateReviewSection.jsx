import { useState } from "react";
import StarRatingInput from "../ui/StarRatingInput";
import { Send } from "lucide-react";

const CreateReviewSection = ({ onSubmitReview }) => {
  const [rating, setRating] = useState(5);
  const [comment, setComment] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!comment.trim()) return;

    if (onSubmitReview) {
      onSubmitReview({
        rating,
        comment: comment.trim(),
      });
    }

    setComment("");
    setRating(5);
  };

  return (
    <div className="max-w-2xl mx-auto p-6 sm:p-8 rounded-2xl bg-brand-dark-paper border border-brand-dark-border space-y-6">
      <div className="space-y-1">
        <h2 className="text-xl font-bold text-white">Write a Review</h2>
        <p className="text-xs text-brand-silver">
          Share your experience with this branch to help others.
        </p>
      </div>

      <form onSubmit={handleSubmit} className="space-y-5">
        <div className="space-y-2">
          <label className="text-xs font-bold text-brand-silver uppercase tracking-wider block">
            Your Rating
          </label>
          <div className="flex items-center gap-3">
            <StarRatingInput rating={rating} onRatingChange={setRating} />
            <span className="text-xs font-semibold text-amber-400 bg-amber-400/10 px-2.5 py-1 rounded-lg">
              {rating} / 5 Stars
            </span>
          </div>
        </div>

        <div className="space-y-2">
          <label className="text-xs font-bold text-brand-silver uppercase tracking-wider block">
            Your Experience
          </label>
          <textarea
            rows={4}
            value={comment}
            onChange={(e) => setComment(e.target.value)}
            placeholder="Tell us about the services, staff, and overall experience..."
            className="w-full bg-brand-dark-bg border border-brand-dark-border rounded-xl p-3.5 text-xs sm:text-sm text-white placeholder-brand-silver/50 focus:outline-none focus:border-brand-red-light transition-colors resize-none"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full sm:w-auto px-6 py-3 rounded-xl bg-brand-red hover:bg-brand-red-hover text-white text-xs font-bold transition-all flex items-center justify-center gap-2 cursor-pointer active:scale-95"
        >
          <Send className="w-3.5 h-3.5" /> Submit Review
        </button>
      </form>
    </div>
  );
};

export default CreateReviewSection;