import { Controller, useForm, useWatch } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Send, Loader2 } from "lucide-react";
import StarRatingInput from "../ui/StarRatingInput";

const reviewSchema = z.object({
  rating: z
    .number()
    .min(1, "Please select at least 1 star")
    .max(5, "Rating cannot exceed 5 stars"),

  comment: z
    .string()
    .trim()
    .min(5, "Comment must be at least 5 characters long")
    .max(500, "Comment is too long (max 500 characters)"),
});

const CreateReviewSection = ({ onSubmitReview }) => {
  const {
    control,
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: zodResolver(reviewSchema),
    defaultValues: {
      rating: 5,
      comment: "",
    },
  });

  const selectedRating = useWatch({
    control,
    name: "rating",
    defaultValue: 5,
  });

  // create Review submission handler
  const onSubmit = async (values) => {
    try {
      if (onSubmitReview) {
        await onSubmitReview({
          rating: values.rating,
          comment: values.comment.trim(),
        });
      }
      reset();
    } catch (error) {
      console.error("Failed to submit review:", error);
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6 sm:p-8 rounded-2xl bg-brand-dark-paper border border-brand-dark-border space-y-6">
      <div className="space-y-1">
        <h2 className="text-xl font-bold text-white">Write a Review</h2>
        <p className="text-xs text-brand-silver">
          Share your experience with this branch to help others.
        </p>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
        <div className="space-y-2">
          <label className="text-xs font-bold text-brand-silver uppercase tracking-wider block">
            Your Rating
          </label>

          <div className="flex items-center gap-3">
            <Controller
              name="rating"
              control={control}
              render={({ field }) => (
                <StarRatingInput
                  rating={field.value}
                  onRatingChange={field.onChange}
                />
              )}
            />

            <span className="text-xs font-semibold text-amber-400 bg-amber-400/10 px-2.5 py-1 rounded-lg">
              {selectedRating} / 5 Stars
            </span>
          </div>

          {errors.rating && (
            <p className="text-xs text-red-500 font-medium">
              {errors.rating.message}
            </p>
          )}
        </div>

        <div className="space-y-2">
          <label className="text-xs font-bold text-brand-silver uppercase tracking-wider block">
            Your Experience
          </label>

          <textarea
            {...register("comment")}
            rows={4}
            placeholder="Tell us about the services, staff, and overall experience..."
            className={`w-full bg-brand-dark-bg border rounded-xl p-3.5 text-xs sm:text-sm text-white placeholder-brand-silver/50 focus:outline-none transition-colors resize-none ${
              errors.comment
                ? "border-red-500/80 focus:border-red-500"
                : "border-brand-dark-border focus:border-brand-red-light"
            }`}
          />

          {errors.comment && (
            <p className="text-xs text-red-500 font-medium">
              {errors.comment.message}
            </p>
          )}
        </div>

        <button
          type="submit"
          disabled={isSubmitting}
          className="w-full sm:w-auto px-6 py-3 rounded-xl bg-brand-red hover:bg-brand-red-hover disabled:opacity-50 disabled:cursor-not-allowed text-white text-xs font-bold transition-all flex items-center justify-center gap-2 cursor-pointer active:scale-95"
        >
          {isSubmitting ? (
            <>
              <Loader2 className="w-3.5 h-3.5 animate-spin" /> Submitting...
            </>
          ) : (
            <>
              {" "}
              <Send className="w-3.5 h-3.5" /> Submit Review
            </>
          )}
        </button>
      </form>
    </div>
  );
};

export default CreateReviewSection;
