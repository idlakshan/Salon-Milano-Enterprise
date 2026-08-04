import { BranchCard } from "./BranchCard"; 

const BRANCHES = [
  {
    city: "Colombo 03 (Flagship)",
    address: "No. 42, Galle Road, Colombo 03",
    phone: "+94 11 234 5678",
    hours: "Mon - Sun: 9:00 AM - 8:00 PM",
    rating: 4.9,
    reviewsCount: "240+",
    image: "https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f?q=80&w=800&auto=format&fit=crop",
  },
  {
    city: "Kandy City",
    address: "No. 18, Peradeniya Road, Kandy",
    phone: "+94 81 222 3456",
    hours: "Mon - Sun: 9:00 AM - 7:30 PM",
    rating: 4.8,
    reviewsCount: "180+",
    image: "https://images.unsplash.com/photo-1621605815971-fbc98d665033?q=80&w=800&auto=format&fit=crop",
  },
  {
    city: "Galle Fort",
    address: "Church Street, Galle Fort",
    phone: "+94 91 224 8900",
    hours: "Tue - Sun: 10:00 AM - 8:00 PM",
    rating: 4.9,
    reviewsCount: "150+",
    image: "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?q=80&w=800&auto=format&fit=crop",
  },
  {
    city: "Negombo Beach",
    address: "Lewis Place, Negombo",
    phone: "+94 31 223 4567",
    hours: "Mon - Sun: 9:00 AM - 8:00 PM",
    rating: 4.7,
    reviewsCount: "110+",
    image: "https://images.unsplash.com/photo-1560066984-138dadb4c035?q=80&w=800&auto=format&fit=crop",
  },
];

export const BranchesSection = () => {
  const gridColsClass =
    BRANCHES.length > 3
      ? "grid-cols-1 sm:grid-cols-2 lg:grid-cols-4"
      : "grid-cols-1 md:grid-cols-3";

  return (
    <section className="py-20 px-6 bg-brand-dark-sidebar/40 border-y border-brand-dark-border">
      <div className="max-w-7xl mx-auto">
        <div className="text-center max-w-2xl mx-auto mb-14">
          <h2 className="text-xs uppercase tracking-widest text-brand-red-light font-bold mb-2">
            Visit Us
          </h2>
          <h3 className="font-display text-3xl sm:text-4xl font-extrabold text-white">
            Our Salon Branches
          </h3>
          <p className="mt-3 text-sm text-brand-silver">
            Locate your nearest Milano Salon and experience luxury grooming near you.
          </p>
        </div>

        <div className={`grid ${gridColsClass} gap-6`}>
          {BRANCHES.map((branch, idx) => (
            <BranchCard key={branch.city + idx} branch={branch} />
          ))}
        </div>
      </div>
    </section>
  );
};