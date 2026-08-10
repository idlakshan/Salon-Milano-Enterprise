import ReviewCard from "../ui/ReviewCard";

const ReviewsSection = ({ reviews }) => {
  return (
    <div className="space-y-6">
      <div className="space-y-4">
        {reviews.map((review) => (
          <ReviewCard key={review.id} review={review} />
        ))}
      </div>
    </div>
  );
};

export default ReviewsSection;
