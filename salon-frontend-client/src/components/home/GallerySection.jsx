const GALLERY = [
  {
    src: "https://plus.unsplash.com/premium_photo-1661290481306-4841edd49719?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fHNhbG9uJTIwbWVufGVufDB8fDB8fHww",
    alt: "Salon chair and mirror station",
    span: "md:col-span-2 md:row-span-2",
  },
  {
    src: "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?q=80&w=700&auto=format&fit=crop",
    alt: "Stylist cutting hair",
    span: "",
  },
  {
    src: "https://images.unsplash.com/photo-1562322140-8baeececf3df?q=80&w=700&auto=format&fit=crop",
    alt: "Hot towel shave",
    span: "",
  },
  {
    src: "https://images.unsplash.com/photo-1519415510236-718bdfcd89c8?q=80&w=700&auto=format&fit=crop",
    alt: "Barber tools laid out",
    span: "",
  },
  {
    src: "https://images.unsplash.com/photo-1487412947147-5cebf100ffc2?q=80&w=900&auto=format&fit=crop",
    alt: "Spa relaxation stones",
    span: "md:col-span-2",
  },
  {
    src: "https://images.unsplash.com/photo-1633681926035-ec1ac984418a?q=80&w=700&auto=format&fit=crop",
    alt: "Manicure treatment",
    span: "",
  },
  {
    src: "https://plus.unsplash.com/premium_photo-1661507250205-79ffef5cdeb5?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8ODN8fHNhbG9uJTIwbWVufGVufDB8fDB8fHww",
    alt: "Barbershop interior",
    span: "",
  },
];

const GallerySection = () => {
  return (
    <section className="max-w-380 mx-auto px-6 py-16">
      <div className="mb-8">
        <span className="uppercase text-xs tracking-widest text-brand-red-light font-semibold block mb-1">
          Gallery
        </span>
        <h2 className="font-display text-3xl md:text-4xl font-extrabold text-white">
          Inside Milano
        </h2>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-4 gap-3 md:gap-4 auto-rows-35 md:auto-rows-40">
        {GALLERY.map((img) => (
          <div
            key={img.src}
            className={`group relative overflow-hidden rounded-2xl bg-brand-dark-paper border border-brand-dark-border ${img.span}`}
          >
            <img
              src={img.src}
              alt={img.alt}
              loading="lazy"
              className="w-full h-full min-h-35 object-cover transition-transform duration-500 group-hover:scale-105"
            />

            <div className="absolute inset-0 bg-linear-to-t from-black/80 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-3 pointer-events-none">
              <span className="text-xs text-white font-medium">{img.alt}</span>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}

export default GallerySection;