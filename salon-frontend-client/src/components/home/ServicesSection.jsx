import ServiceFeatureItem from "../ui/ServiceFeatureItem";

const FEATURES = [
  "Certified Senior Stylists",
  "Premium Imported Products",
  "Luxury & Modern Ambience",
  "Personalized Care & Consultation",
];

const ServicesSection = () => {
  return (
    <section className="bg-brand-dark-bg py-16 md:py-24 px-6 border-t border-brand-dark-border">
      <div className="max-w-7xl mx-auto grid grid-cols-1 lg:grid-cols-12 gap-10 items-center">
        <div className="lg:col-span-5 relative rounded-3xl overflow-hidden border border-brand-dark-border group">
          <img
            src="https://media.istockphoto.com/id/1773270658/photo/professional-hairdresser-working-with-bearded-client-in-barbershop-closeup-black-and-white.jpg?s=612x612&w=0&k=20&c=VmQ_uvNngyD9nMayihYVZARK9FAmUQiMI6rqiT9ZwOM="
            alt="Milano Salon Luxury Experience"
            className="w-full h-100 lg:h-120 object-cover transition-transform duration-700 group-hover:scale-105"
          />
          <div className="absolute inset-0 bg-linear-to-t from-black/60 via-transparent to-transparent" />
        </div>

        <div className="lg:col-span-7 flex flex-col justify-center">
          <span className="uppercase text-xs tracking-widest text-brand-red-light font-bold mb-2 block">
            Milano Experience
          </span>

          <h2 className="font-display text-3xl sm:text-4xl lg:text-5xl font-extrabold text-white leading-tight mb-6">
            Our Services
          </h2>

          <p className="text-sm sm:text-base text-brand-silver leading-relaxed mb-8">
            We offer a comprehensive range of beauty treatments designed to meet
            all your needs and exceed your expectations. Our expert team is
            dedicated to providing you with an exceptional experience, using the
            latest techniques and top-quality products. Whether you're looking
            for a refreshing new look or a relaxing retreat, we have the perfect
            solution to enhance your natural beauty and boost your confidence.
            Experience the epitome of beauty at Milano Salon, where your
            satisfaction is our top priority.
          </p>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 border-t border-brand-dark-border pt-6">
            {FEATURES.map((feature, idx) => (
              <ServiceFeatureItem key={idx} text={feature} />
            ))}
          </div>
        </div>
      </div>
    </section>
  );
};

export default ServicesSection;
