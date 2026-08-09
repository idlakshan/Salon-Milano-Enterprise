import { BRANCHES } from "../../data/servicesData";
import BranchCard from "../ui/BranchCard";

const BranchesSection = () => {
  const gridColsClass =
    BRANCHES.length > 3
      ? "grid-cols-1 sm:grid-cols-2 lg:grid-cols-4"
      : "grid-cols-1 md:grid-cols-3";

  return (
    <section className="py-20 px-6 bg-brand-dark-sidebar/40 border-y border-brand-dark-border">
      <div className="max-w-380 mx-auto">
        <div className="text-center max-w-2xl mx-auto mb-14">
          <h2 className="text-xs uppercase tracking-widest text-brand-red-light font-bold mb-2">
            Visit Us
          </h2>
          <h3 className="font-display text-3xl sm:text-4xl font-extrabold text-white">
            Our Salon Branches
          </h3>
          <p className="mt-3 text-sm text-brand-silver">
            Locate your nearest Milano Salon and experience luxury grooming near
            you.
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

export default BranchesSection;
