const ServiceCategorySidebar = ({
  categories,
  selectedCategory,
  onSelectCategory,
}) => {
  return (
    <div className="lg:col-span-3 space-y-2 bg-brand-dark-paper p-4 rounded-2xl border border-brand-dark-border">
      <h3 className="text-xs font-bold uppercase tracking-wider text-brand-silver mb-3 px-2">
        Categories
      </h3>
      {categories.map((cat) => (
        <button
          key={cat.id}
          onClick={() => onSelectCategory(cat.id)}
          className={`w-full text-left px-4 py-3 rounded-xl text-xs sm:text-sm font-semibold transition-all cursor-pointer ${
            selectedCategory === cat.id
              ? "bg-brand-red text-white font-bold shadow-md"
              : "bg-brand-dark-bg text-brand-silver hover:text-white hover:bg-brand-dark-border/40"
          }`}
        >
          {cat.name}
        </button>
      ))}
    </div>
  );
};

export default ServiceCategorySidebar;
